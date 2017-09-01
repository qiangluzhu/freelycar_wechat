import request from '../utils/request';
export default {
    getCardList: (params,option) => {
        return request('/api/service/list',option,params);
    }
}