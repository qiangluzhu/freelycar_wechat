import request from '../utils/request';

export default {
  //短信验证接口
  identifyCode: (option) => {
    return request('/api/sms/request');
  },
  loginBind: (option) => {
    return request('/api/sms/bind',option)
  },

  //会员接口
  addWxUser:(option) =>{
    return request('/api/sms/bind',option)
  },
  login:(option)=>{
    return request('/api/user/login',option)
  },
  logout:(option)=>{
    return request('/api/user/logout',option)
  },
  myDiscount:(option)=>{
    return request('/api/user/myDiscount',option)
  },//客户优惠券信息
  myPoints:(option)=>{
    return request('/api/user/points',option)
  },

} 
