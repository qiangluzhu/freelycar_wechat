import get from '../utils/get';
import post from '../utils/post';
export default {
    //消费单据支付
    payment: (params) => {
        return post('/api/orders/payment',params);
    },
    //消费服务单据列表
    listConsumOreder:(params)=>{
        return get('/api/orders/listConsumOreder',params)
    },
    listWXPayOrder:(params)=>{
        return get('/api/orders/listWXPayOrder',params)
    },
    //单据评价
    orderComment:(params)=>{
        return post('/api/orders/comment',params)
    },
    orderDetail:(params)=>{
        return get('/api/orders/detail',params)
    }
}