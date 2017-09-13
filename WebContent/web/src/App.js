
const React = require('react');
const Router = require('react-router-dom');
const Route = Router.Route;
const Switch = Router.Switch;
import IndexPage from './routes/IndexPage';
import Personalcenter from './routes/personalCenter/Personalcenter'
import PersonalInfo from './routes/personalInfo/PersonalInfo'
import Login from './routes/auth/Login'
import AddCar from './routes/addCar/AddCar'
import SelectCarInfo from './routes/addCar/SelectCarBrand'
import CarBrand from './routes/addCar/CarBrand'

import OrderTrack from './routes/order/OrderTrack'
import ServiceCard from './routes/service/ServiceCard'
import OrderDetail from './routes/orderDetail/orderDetail'
import OrderDetail_nopay from './routes/orderDetail/orderDetail_nopay'
import OrderDetail_vip from './routes/orderDetail/orderDetail_vip'

import AddCard from './routes/addCard/addCard'

import MyCardDetail from './routes/membership/MyCardDetail'
import MyCard from './routes/membership/MyCard'
import MyPoints from './routes/membership/MyPoints'
//车险询价
import Inquiry from './routes/autoInsurance/Inquiry'

//爱车信息
import CarInfo from './routes/carInfo/CarInfo'

//保险信息
import Insurance from './routes/insuranceInfo/Insurance'

//门店 
import CooperativeStore from './routes/store/CooperativeStore'
import StoreDetail from './routes/store/StoreDetail'
import CommentStore from './routes/store/CommentStore'
import MyFavour from './routes/store/MyFavour'
//404 not found页面
import NotFound from './routes/NotFound'
const App = () => (
    <div>
        <Switch>
            <Route exact path="/" component={Personalcenter}/>
            <Route path="/center" component={Personalcenter} />
            <Route path="/login" component={Login} />
      
            <Route path="/addcar" component={AddCar} />
            <Route path="/addcar/carbrand" component={CarBrand} />
      
            <Route path="/ordertrack" component={OrderTrack} />
            <Route path="/personalInfo" component={PersonalInfo} />
            <Route path="/serviceCard" component={ServiceCard} />
      
            <Route path="/orderDetail/:id" component={OrderDetail} />
            <Route path="/orderDetail_nopay" component={OrderDetail_nopay} />
            <Route path="/orderDetail_vip" component={OrderDetail_vip} />
            <Route path="/addCard" component={AddCard} />
            <Route path="/membership/mycard" component={MyCard} />
            <Route path="/membership/mycard/detail/:id" component={MyCardDetail}/>
            <Route path="/membership/mypoints" component={MyPoints}/>
            <Route path="/membership/myfavour" component={MyFavour}/>
      
            <Route path="/carInfo" component={CarInfo}/>
            <Route path="/store/cooperative-store" component={CooperativeStore}/>
            <Route path="/store/comment/:consumerOrderId" component={CommentStore}/>
            <Route path="/store/detail/:storeId" component={StoreDetail}/>
            <Route path="/insurance/:clientId/:carId" component={Insurance}/>
            <Route path="/insurance/inquiry" component={Inquiry}/>
            <Route path="/notFound" component={NotFound}/>
        </Switch>
    </div>
);

module.exports = App;