import get from '../utils/get';
import post from '../utils/post';
export default {
    buyCard: (params) => {
        return post('api/pay/buycard',params);
    },
    buySales:(option)=>{
        return post('api/pay/buysales',option)
    },
    payment:(option)=>{
        return get('api/pay/payment',option)
    },

    getWXConfig:(option) =>{
        return get('api/pay/wx/getJSSDKConfig',option)
    }



}