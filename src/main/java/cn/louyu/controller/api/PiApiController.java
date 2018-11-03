package cn.louyu.controller.api;

import cn.louyu.models.ResultMsg;
import cn.louyu.service.pi.GpioUnit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/pi")
public class PiApiController {
    //GpioUnit 实例
    private GpioUnit gpioUnit;

    {
        try {
            gpioUnit=GpioUnit.getInstance();
        }catch (UnsatisfiedLinkError e){

        }
    }


    @RequestMapping("/setPin")
    @ResponseBody
    public ResultMsg setPin(String pinName,boolean state){
        ResultMsg msg=isGpioUnitNull();
        if(!msg.Success)return msg;
        msg=gpioUnit.setGpioPinState(pinName, state);
        return msg;
    }

    @RequestMapping("/getPin")
    @ResponseBody
    public ResultMsg getPin(String pinName){
        ResultMsg msg=isGpioUnitNull();
        if(!msg.Success)return msg;
        msg=gpioUnit.getGpioPinState(pinName);
        return msg;
    }


    private ResultMsg isGpioUnitNull(){
        ResultMsg msg=new ResultMsg();
        if(gpioUnit==null){
            msg.Msg="获取GPIO控制器实例失败。";
            return msg;
        }
        msg.Success=true;
        return msg;
    }
}
