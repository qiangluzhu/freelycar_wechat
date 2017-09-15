import React from 'react';
import NavBar from '../../components/NavBar'
import { Flex } from 'antd-mobile'
import './OrderTrack.less'
import { orderDetail } from '../../services/orders.js'
import parseForm from '../../utils/parseToForm.js'
import PropTypes from 'prop-types';
class OrderTrack extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            payState: 0,
            state: 0,
            projects: [],
            programName: '',
            licensePlate: '',
            clientName: '',
            deliverTime:'',
            finishTime:'',
            createDate:''
        }
    }

    componentDidMount() {
        orderDetail({
            consumOrderId: this.props.match.params.id
        }).then((res) => {
            let data = res.data.orders
            console.log(res)
            if (res.data.code == '0') {
                console.log(data)
                this.setState({
                    clientName: data.clientName,
                    licensePlate: data.licensePlate,
                    createDate:data.createDate,
                    deliverTime:data.deliverTime,
                    finishTime:data.finishTime,
                    programName: data.programName,
                    // state: data.state == 0 ? '已接车' : (data.state == 1 ? '已完工' : '已交车'),
                    state: data.state,
                    projects: data.projects,
                    payState: data.payState,
                    totalPrice: data.totalPrice,
                })
                window.localStorage.setItem('storeName',data.store.name)
                window.localStorage.setItem('imgUrl',data.store.imgUrls[0])
            }
        }).catch((error) => { console.log(error) })
    }

    render() {
        let projectItems = this.state.projects.map((item, index) => {
            return <Flex direction="column"  justify="center" style={{width:'100%',borderTop:'1px dashed #f0f0f0', height: '0.85rem'}} key={index} align="end" >
                <Flex style={{width:'100%'}} >
                    <div>{item.projectInfo.name}</div>
                    <div style={{marginLeft:'auto'}}><span style={{ fontSize: '.24rem' }}>￥</span>200</div>
                </Flex>
                {/* <Flex.Item className="total-money" >
                        <p className="primary-money">
                            <span style={{ fontSize: '.08rem' }}>￥</span>200
                        <i>
                            </i>
                        </p>
                    </Flex.Item> */}
                <Flex  className="order-track-remain" style={{width:'100%'}}>
                    <div>
                        <p>已抵扣会员卡{item.cardNumber}，该项目还剩余{item.remaining}次</p>
                    </div>
                    <p style={{marginLeft:'auto',fontSize:'.22rem'}}>
                        <span style={{ fontSize: '.22rem'}}>￥</span>200
                        <i>
                        </i>
                    </p>
                </Flex>
            </Flex>
        })
        return <div className="body-bac">
            <NavBar title="订单跟踪" />
            <Flex className="order-track-baseinfo">
                <Flex.Item className="Info">
                    <p>{this.state.clientName}</p>
                    <div>{this.state.licensePlate}</div>
                </Flex.Item>
                <Flex.Item className="state">{this.state.state==1?'已接车':(this.state.state==2?'已完成':'已交车')}</Flex.Item>
            </Flex>
            <div className="order-track-line"></div>
            <Flex className="order-track-program" direction="column">
                <div className="beauty special-font-size">
                    <p>{this.state.programName}</p>
                </div>
                <div className="ordertrack-project">{projectItems}</div>

                <div style={{ textAlign: 'right', width: '100%', marginBottom: '.1rem' }}><div style={{ marginRight: '.5rem', display: 'inline-block' }}>合计：<span style={{ fontSize: '.24rem', color: '#e42f2f' }}>￥</span><span style={{ color: '#e42f2f' }}>{this.state.totalPrice}</span></div>
                    {this.state.payState == 0 && <div className="pay-btn">
                        <p>去付款</p>
                    </div>}
                </div>
            </Flex>

            <div className='order-list order-tarck-info'>
                <Flex style={{ height: '100%' }}>
                    <Flex.Item className='leftLable'>订单编号</Flex.Item>
                    <Flex.Item className='rightText'>{this.props.match.params.id}</Flex.Item>
                </Flex>
            </div>
            <div className='order-list order-tarck-info' style={{ marginTop: '0',borderTop:'1px dashed #f0f0f0' }}>
                <Flex style={{ height: '100%' }}>
                    <Flex.Item className='leftLable'>订单时间</Flex.Item>
                    <Flex.Item className='rightText'>2017-08-07 10:00:00</Flex.Item>
                </Flex>
            </div>
            <div className="order-track-state">
                <Flex className={this.state.state == 3 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>{this.state.deliverTime.slice(5,10)}</p>
                        <p style={{ marginLeft: '.22rem' }}>{this.state.deliverTime.slice(11,16)}</p>
                    </div>
                    <div className="right-box">
                        <div className="circle">
                        </div>
                        <div className="car-state">已交车
                        </div>
                        <div>爱车已交回你的手中 快来评价获积分吧
                        </div>
                        <div className="evaluate" onClick={() => { this.context.router.history.push('/store/comment') }}>
                            评价得200积分
                        </div>
                    </div>
                </Flex>
                <Flex className={this.state.state == 2 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>{this.state.finishTime.slice(5,10)}</p>
                        <p style={{ marginLeft: '.22rem' }}>{this.state.finishTime.slice(11,16)}</p>
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
                <Flex className={this.state.state == 1 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>{this.state.createDate.slice(5,10)}</p>
                        <p style={{ marginLeft: '.22rem' }}>{this.state.createDate.slice(11,16)}</p>
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
                <Flex className={this.state.state == 1 ? "order-track-state-box active" : "order-track-state-box"} align="start">
                    <div className="time">
                        <p>{this.state.createDate.slice(5,10)}</p>
                        <p style={{ marginLeft: '.22rem' }}>{this.state.createDate.slice(11,16)}</p>
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
OrderTrack.contextTypes = {
    router: PropTypes.object.isRequired
}