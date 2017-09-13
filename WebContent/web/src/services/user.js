import post from '../utils/post';
import get from '../utils/get';

export default {
    addWxUser: (params) => {
        return post('api/user/addWxUser', params)
    },

    login: (params) => {
        return post('api/user/login', params)
    },

    logout: (params) => {
        return post('api/user/logout', params)
    },

    myDiscount: (params) => {
        return post('api/user/myDiscount', params)
    },//客户优惠券信息

    myPoints: (params) => {
        return get('api/user/points', params)
    },

    addCar: (params) => {
        return post('api/user/addCar', params)
    },

    userDetail:(params)=>{
        return get('api/user/detail',params)
    },

    delCar:(params)=>{
        return post('api/user/delCar',params)
    },

    defaultCar:(params)=>{
        return get('api/user/defaultCar',params)
    },

    modifyCarInfo:(params)=>{
        return get('api/user/carInfo',params)
    },

    wxInfo:(params)=>{
        return get('api/user/wxInfo',params)
    },

    myCard:(params)=>{
        return get('api/user/listCard',params)
    },

    myCar:(params)=>{
        return get('api/user/listCar',params)
    },
    detailInfo:(params)=>{
        return get('api/user/detailInfo',params)
    },
    carDetail:(params)=>{
        return get('api/user/carDetail',params)
    },

}