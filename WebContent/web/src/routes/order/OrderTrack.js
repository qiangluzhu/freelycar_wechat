import React from 'react';
import NavBar from '../../components/NavBar'
import { Flex } from 'antd-mobile'
import './OrderTrack.less'
class OrderTrack extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            paystate: 0,
            carState:0,
        }
    }

    render() {
        return <div className="body-bac">
            <NavBar title="订单跟踪" />
            <Flex className="order-track-baseinfo">
                <Flex.Item className="Info">
                    <p>姓名：何梦成</p>
                    <div>牌照号：苏A233333</div>
                </Flex.Item>
                <Flex.Item className="state">已交车</Flex.Item>
            </Flex>
            <div className="order-track-line"></div>
            <Flex className="order-track-program" style={{ height: this.state.paystate == 0 ? '2.24rem' : '1.8rem' }} direction="column">
                <div className="beauty">
                    <p>美容</p>
                </div>
                <Flex className="Info" >
                    <i className="circle"></i>
                    <p>内饰除菌SPA</p>
                    <Flex.Item className="total-money" >
                        <p className="actual-money">￥200</p>
                        <p className="primary-money">
                            ￥200
                            <i>
                            </i>
                        </p>
                    </Flex.Item>
                </Flex>
                <div className="beauty overplus"><p>已抵扣会员卡00001 普洗项目1次，该项目还剩余20次</p></div>
                {this.state.paystate != 0 && <Flex className="order-track-time" style={{ width: '100%', fontSize: '.22rem', color: '#8e8e8e' }}>
                    <i className="circle2"></i>
                    <p>2017/07/25 14:23</p>
                </Flex>}
                {this.state.paystate == 0 && <div className="pay-btn">
                    <p>去付款</p>
                </div>}
            </Flex>
            <div className="order-track-state">
                <Flex className={this.state.carState==0?"order-track-state-box active":"order-track-state-box"} align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{marginLeft:'.22rem'}}>14:30</p>
                    </div>
                    <div className="right-box">
                        <div className="circle">
                        </div>
                        <div className="car-state">已交车
                        </div>
                        <div>爱车已交回你的手中 快来评价获积分吧
                        </div>
                        <div className="evaluate">
                            评价得200积分
                        </div>
                    </div>
                </Flex>
                <Flex className={this.state.carState==1?"order-track-state-box active":"order-track-state-box"}  align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{marginLeft:'.22rem'}}>14:30</p>
                    </div>
                    <div className="right-box">
                        <div className="circle">
                        </div>
                        <div className="car-state">已完工
                        </div>
                        <div>爱车已交回你的手中 快来评价获积分吧
                        </div>
                    </div>
                </Flex>
                <Flex className={this.state.carState==2?"order-track-state-box active":"order-track-state-box"}  align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{marginLeft:'.22rem'}}>14:30</p>
                    </div>
                    <div className="right-box">
                        <div className="circle">
                        </div>
                        <div className="car-state">商家已接车
                        </div>
                        <div>爱车已交回你的手中 快来评价获积分吧
                        </div>
                    </div>
                </Flex>
                <Flex className={this.state.carState==3?"order-track-state-box active":"order-track-state-box"}  align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{marginLeft:'.22rem'}}>14:30</p>
                    </div>
                    <div className="right-box last">
                        <div className="circle">
                        </div>
                        <div className="car-state">已下单
                        </div>
                        <div>爱车已交回你的手中 快来评价获积分吧
                        </div>
                    </div>
                </Flex>
            </div>
        </div>
    }
}
export default OrderTrack