package cn.louyu.service.pi;

import cn.louyu.models.ResultMsg;
import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

public class GpioUnit {
    //GPIO引脚封装集合
    private final Map<String, GpioPinDigitalMultipurpose> gpioMap;
    //GPIO控制器单例
    private final GpioController gpio;
    //GpioUnit 单例
    private static GpioUnit instance=null;

    public static GpioUnit getInstance(){
        if(instance==null){
            synchronized (GpioUnit.class){
                if (instance==null){
                    instance=new GpioUnit();
                }
            }
        }
        return instance;
    }

    private GpioUnit(){
        this(null);
    }

    private GpioUnit(GpioPinListener pinListener){
        gpioMap=new HashMap<String, GpioPinDigitalMultipurpose>();
        gpio=GpioFactory.getInstance();
        for (Pin pin:RaspiPin.allPins()) {
            try {
                //拿到PIN引脚，并设置为输出模式
                GpioPinDigitalMultipurpose gpioPin = gpio.provisionDigitalMultipurposePin(pin, pin.getName(), PinMode.DIGITAL_OUTPUT);
                //添加引脚状态监听
                if (pinListener != null)
                    gpioPin.addListener(pinListener);
                //配置引脚关闭行为; 这些设置将是
                //终止应用程序时自动应用于引脚
                //确保在应用程序关闭引脚
                gpioPin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
                //添加到Map
                gpioMap.put(pin.getName(), gpioPin);
            }catch (RuntimeException e){
                continue;
            }
        }
    }


    public final void setGpioPinListener(GpioPinListener listener){
        if(listener==null) return;
        for ( Map.Entry<String, GpioPinDigitalMultipurpose> entry:gpioMap.entrySet()){
            entry.getValue().addListener(listener);
        }
    }

    public final Map<String, GpioPinDigitalMultipurpose> getGpioMap() {
        return gpioMap;
    }

    public final GpioController getGpio() {
        return gpio;
    }

    public final String getPinName(Pin pin){
        return pin.getName();
    }

    public final GpioPinDigitalMultipurpose getGpioPinByName(String name){
        return gpioMap.get(name);
    }

    public final GpioPinDigitalMultipurpose getGpioPinByPin(Pin pin){
        return getGpioPinByName(pin.getName());
    }

    /**
     * 设置引脚模式
     * @param name 引脚名称
     * @param mode 引脚模式
     * @return 返回true表示设置成功
     * */
    public final ResultMsg setGpioPinModel(String name,PinMode mode){
        ResultMsg msg=new ResultMsg();
        GpioPinDigitalMultipurpose gpioPin = getGpioPinByName(name);
        if(gpioPin==null){
            msg.Msg="引脚未找到。";
            return msg;
        }
        gpioPin.setMode(mode);
        if(gpioPin.getMode().getValue()==mode.getValue()){
            msg.Success=true;
            msg.Msg="设置"+name+"模式为"+gpioPin.getMode().getName()+"模式。";
        }else {
            msg.Msg="设置"+name+"模式失败。";
        }
        return msg;
    }

    /**
     * 设置引脚模式
     * @param pin 引脚
     * @param mode 引脚模式
     * @return 返回true表示设置成功
     * */
    public final ResultMsg setGpioPinModel(Pin pin,PinMode mode){
        return setGpioPinModel(getPinName(pin),mode);
    }

    /**
     * 获取引脚状态
     * @return 高电平返回1 ，低电平返回0 ，未知的(当获取为空)返回 -1;
     * */
    public final ResultMsg getGpioPinState(Pin pin){
        return getGpioPinState(getPinName(pin));
    }

    /**
     * 获取引脚状态
     * @return 高电平返回msg.Status=1 ，低电平返回msg.Status=0 ，未知的(当获取为空)返回 msg.Status=-1;
     * */
    public final ResultMsg getGpioPinState(String name){
        ResultMsg msg=new ResultMsg();
        GpioPinDigitalMultipurpose gpioPin = getGpioPinByName(name);
        if(gpioPin==null){
            msg.Status=-1;
            msg.Msg="引脚未找到。";
            return msg;
        }
        msg.Status=gpioPin.getState().getValue();
        msg.Success=true;
        msg.Msg=msg.Status==1?name+"为高电平":name+"为低电平";
        return msg;
    }

    /**
     * 设置指定引脚的状态
     * @param name 引脚名称
     * @param state 状态
     * @return 设置成功返回 msg.Status= 0 or1  msg.Success=true;
     * */
    public final ResultMsg setGpioPinState(String name, boolean state){
        ResultMsg msg=new ResultMsg();
        msg.Status=-1;
        GpioPinDigitalMultipurpose gpioPin = getGpioPinByName(name);
        if(gpioPin==null){
            msg.Msg=name+"引脚未找到。";
            return msg;
        }
        if(state) gpioPin.high();
        else gpioPin.low();
        if(state?gpioPin.isHigh():gpioPin.isLow()){
            msg.Status=state?1:0;
            msg.Success=true;
            msg.Msg="设置"+name+"为"+(state?"高":"低")+"电平。";
        }else {
            msg.Msg="操作失败。";
        }
        return msg;
    }

    /**
     * 设置指定引脚的状态
     * @param pin 引脚
     * @param state 状态
     * @return 设置成功返回true
     * */
    public final ResultMsg setGpioPinState(Pin pin, boolean state){
        return setGpioPinState(getPinName(pin),state);
    }
}
