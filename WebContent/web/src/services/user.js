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

    userDetail:(params)=>{
        return get('/api/user/detail',params)
    },

    delCar:(params)=>{
        return post('/api/user/delCar',params)
    },

    defaultCar:(params)=>{
        return post('/api/user/defaultCar',params)
    },

    modifyCarInfo:(params)=>{
        return post('/api/user/carInfo',params)
    },

    wxInfo:(params)=>{
        return get('/api/user/wxInfo',params)
    }
}