import request from '../utils/request';

export default {
    addWxUser: (option) => {
        return request('/api/user/addWxUser', option)
    },

    login: (option) => {
        return request('/api/user/login', option)
    },

    logout: (option) => {
        return request('/api/user/logout', option)
    },

    myDiscount: (option) => {
        return request('/api/user/myDiscount', option)
    },//客户优惠券信息

    myPoints: (option) => {
        return request('/api/user/points', option)
    },

    addCar: (option) => {
        return request('/api/user/addcar', option)
    },

    userDetail:(option)=>{
        console.log(option)
        return request('/api/user/detail',option)
    }
}