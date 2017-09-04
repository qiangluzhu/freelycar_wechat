import post from '../utils/post';

export default {
  //短信验证接口
  identifyCode: (params) => {
    return post('/api/sms/request', params);
  },
  loginBind: (params) => {
    return post('/api/sms/bind', params)
  }
}