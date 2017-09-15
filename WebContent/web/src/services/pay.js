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

<<<<<<< HEAD
    activity:(option)=>{
        return get('api/pay/activity',option)
    },
=======
>>>>>>> fe5926f33a558b73a88ab2c1a9a380462a8c13f8
    getWXConfig:(option) =>{
        return get('api/pay/wx/getJSSDKConfig',option)
    }



}