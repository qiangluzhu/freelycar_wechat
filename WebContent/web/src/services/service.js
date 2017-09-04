import request from '../utils/request';
export default {
    getCardList: (option) => {
        return request('/api/service/list');
    }
}