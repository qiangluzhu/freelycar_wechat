import request from '../utils/request';
export default {
    buyCard: (option) => {
        return request('/api/pay/buycard',option);
    },
    buySales:(option)=>{
        return request('/api/pay/buysales',option)
    }
}