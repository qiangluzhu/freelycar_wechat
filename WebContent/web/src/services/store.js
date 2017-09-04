import post from '../utils/post';
import get from '../utils/get';
export default {
    storeList: (params) => {
        return get('/api/store/list', params);
    },
    storeDetail: (params) => {
        return get('/api/store/detai/',params)
    },
    storeComment: (params) => {
        return get('/api/store/comment', option)
    }
}