import React from 'react';
import NavBar from '../../components/NavBar'
import { Flex } from 'antd-mobile'
import './OrderTrack.less'
import { orderDetail } from '../../services/orders.js'
import parseForm from '../../utils/parseToForm.js'
import {browserHistory} from 'dva/router'
class OrderTrack extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            payState: 0,
            state: 0,
            projects: [],
            programName: '',
            licensePlate: '',
            clientName: ''
        }
    }

    componentDidMount() {
        orderDetail({
            consumOrderId: 'S201708104lfl5v'
        }).then((res) => {
            let data = res.data.orders
            console.log(res)
            if (res.data.code == '0') {
                console.log(data)
                this.setState({
                    clientName: data.clientName,
                    licensePlate: data.licensePlate,
                    programName: data.programName,
                    state: data.state == 0 ? '已接车' : (data.state == 1 ? '已完成' : '已交车'),
                    projects: data.projects,
                    payState: data.payState == 0 ? '未付款' : '已付款',
                    totalPrice: data.totalPrice,
                    state: data.state
                })
            }
        }).catch((error) => { console.log(error) })
    }

    render() {
        let projectItems = this.state.projects.map((item, index) => {
            return <Flex direction="column" style={{ width: '100%' }} key={index}>
                <Flex className="Info">
                    <i className="circle"></i>
                    <p>{item.projectInfo.name}</p>
                    <Flex.Item className="total-money" >
                        <p className="actual-money"><span style={{ fontSize: '.08rem' }}>￥</span>200</p>
                        <p className="primary-money">
                            <span style={{ fontSize: '.08rem' }}>￥</span>200
                        <i>
                        </i>
                        </p>
                    </Flex.Item>
                </Flex>
                <div className="beauty overplus">
                    <p>已抵扣会员卡{item.cardNumber} {item.name}项目1次，该项目还剩余{item.remaining}次</p>
                </div>
            </Flex>
        })
        return <div className="body-bac">
            <NavBar title="订单跟踪" />
            <Flex className="order-track-baseinfo">
                <Flex.Item className="Info">
                    <p>姓名：{this.state.clientName}</p>
                    <div>牌照号：{this.state.licensePlate}</div>
                </Flex.Item>
                <Flex.Item className="state">{this.state.state}</Flex.Item>
            </Flex>
            <div className="order-track-line"></div>
            <Flex className="order-track-program" direction="column">
                <div className="beauty">
                    <p>{this.state.programName}</p>
                </div>
                {projectItems}

                {this.state.payState != 0 && <Flex className="order-track-time" style={{ width: '100%', fontSize: '.22rem', color: '#8e8e8e' }}>
                    <i className="circle2"></i>
                    <p>2017/07/25 14:23</p>
                </Flex>}
                <div style={{ textAlign: 'right', width: '100%', marginBottom: '.1rem' }}><div style={{ marginRight: '.5rem', display: 'inline-block' }}>合计：<span style={{ fontSize: '.16rem', color: '#e42f2f' }}>￥</span><span style={{ color: '#e42f2f' }}>{this.state.totalPrice}</span></div>
                    {this.state.payState == 0 && <div className="pay-btn">
                        <p>去付款</p>
                    </div>}
                </div>
            </Flex>
            <div className="order-track-state">
                <Flex className={this.state.state == 0 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{ marginLeft: '.22rem' }}>14:30</p>
                    </div>
                    <div className="right-box">
                        <div className="circle">
                        </div>
                        <div className="car-state">已交车
                        </div>
                        <div>爱车已交回你的手中 快来评价获积分吧
                        </div>
                        <div className="evaluate" onClick={()=>{browserHistory.push('/store/comment')}}>
                            评价得200积分
                        </div>
                    </div>
                </Flex>
                <Flex className={this.state.state == 1 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{ marginLeft: '.22rem' }}>14:30</p>
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
                <Flex className={this.state.state == 2 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{ marginLeft: '.22rem' }}>14:30</p>
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
                <Flex className={this.state.state == 2 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>07-30</p>
                        <p style={{ marginLeft: '.22rem' }}>14:30</p>
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