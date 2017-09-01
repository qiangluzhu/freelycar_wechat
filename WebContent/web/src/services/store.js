import request from '../utils/request';
export default {
    //消费单据支付
    storeList: (option) => {
        return request('/api/store/list', option);
    },
    //消费单据列表
    storeDetail: (option,params) => {
        return request('/api/store/detai/', option,params)
    },
    //单据评价
    storeComment: (option) => {
        return request('/api/store/comment', option)
    }
}