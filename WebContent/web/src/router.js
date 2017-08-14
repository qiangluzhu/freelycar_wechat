import React from 'react';
import { Router, Route } from 'dva/router';
import IndexPage from './routes/IndexPage';
import Personalcenter from './routes/personalCenter/Personalcenter'
import PersonalInfo from './routes/personalInfo/PersonalInfo'
import Login from './routes/auth/Login'
import AddCar from './routes/addCar/AddCar'
import SelectCarInfo from './routes/addCar/SelectCarBrand'
import OrderTrack from './routes/order/OrderTrack'
import ServiceCard from './routes/service/ServiceCard'
import OrderDetail from './routes/orderDetail/orderDetail'
import OrderDetail_nopay from './routes/orderDetail/orderDetail_nopay'
import OrderDetail_vip from './routes/orderDetail/orderDetail_vip'
import './routes/IndexPage.less'
function RouterConfig({ history }) {
  return (
    <Router history={history}>
      <Route path="/" component={SelectCarInfo} />
      <Route path="/center" component={Personalcenter} />
      <Route path="/login" component={Login} />
      <Route path="/addcar" component={AddCar}/>
      <Route path="/ordertrack" component={OrderTrack}/>
      <Route path="/selectCarInfo" component={SelectCarInfo} />
      <Route path="/personalInfo" component={PersonalInfo} />
      <Route path="/serviceCard" component={ServiceCard} />
      <Route path="/orderDetail" component={OrderDetail} />
      <Route path="/orderDetail_nopay" component={OrderDetail_nopay} />
      <Route path="/orderDetail_vip" component={OrderDetail_vip} />
    </Router>
  );
}

export default RouterConfig;
