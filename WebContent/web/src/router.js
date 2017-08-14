import React from 'react';
import { Router, Route } from 'dva/router';
import IndexPage from './routes/IndexPage';
import Personalcenter from './routes/personalCenter/Personalcenter'
import PersonalInfo from './routes/personalInfo/PersonalInfo'
import Login from './routes/auth/Login'
import AddCar from './routes/addCar/AddCar'
import SelectCarInfo from './routes/addCar/SelectCarBrand'
import CarBrand from './routes/addCar/CarBrand'

import OrderTrack from './routes/order/OrderTrack'
import ServiceCard from './routes/service/ServiceCard'
import './routes/IndexPage.less'
function RouterConfig({ history }) {
  return (
    <Router history={history}>
      <Route path="/" component={SelectCarInfo} />
      <Route path="/center" component={Personalcenter} />
      <Route path="/login" component={Login} />
      <Route path="/addcar" component={AddCar}/>
      <Route path="/addcar/carbrand" component={CarBrand}/>
      <Route path="/ordertrack" component={OrderTrack}/>
      <Route path="/selectCarInfo" component={SelectCarInfo} />
      <Route path="/personalInfo" component={PersonalInfo} />
      <Route path="/serviceCard" component={ServiceCard} />
    </Router>
  );
}

export default RouterConfig;
