import request from '../utils/request';
export default {
    //消费单据支付
    payment: (option) => {
        return request('/api/orders/payment',option);
    },
    //消费单据列表
    orderList:(option)=>{
        return request('/api/orders/list',option)
    },
    //单据评价
    orderComment:(option)=>{
        return request('/api/orders/comment',option)
    },
    orderDetail:(params,option)=>{
        return request('/api/orders/detail/',option,params)
    }
}