import post from '../utils/post';
import get from '../utils/get';

export default {
    addWxUser: (params) => {
        return post('/api/user/addWxUser', params)
    },

    login: (params) => {
        return post('/api/user/login', params)
    },

    logout: (params) => {
        return post('/api/user/logout', params)
    },

    myDiscount: (params) => {
        return post('/api/user/myDiscount', params)
    },//客户优惠券信息

    myPoints: (params) => {
        return post('/api/user/points', params)
    },

    addCar: (params) => {
        return post('/api/user/addcar', params)
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
        return post('/api/user/wxInfo',params)
    }

}