import get from '../utils/get';
export default {
    getCardList: (params) => {
        return get('/api/service/list', params);
    }
}