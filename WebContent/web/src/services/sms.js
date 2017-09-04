import request from '../utils/request';

export default {
    //短信验证接口
    identifyCode: (option) => {
      return request('/api/sms/request');
    },
    loginBind: (option) => {
      return request('/api/sms/bind', option)
    }
}