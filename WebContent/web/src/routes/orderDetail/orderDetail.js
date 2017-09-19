import React from 'react';
import './orderDetail.less'
import { Flex } from 'antd-mobile'
import NavBar from '../../components/NavBar'
import { orderDetail } from '../../services/orders.js'
class OrderDetail extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            clientName: '',
            licensePlate: '',
            state: '',
            totalPrice: 0,
            id: '',
            createDate: '',
            payMethod: 0,
            payState: 0,
            projects: [],

        }
    }


    componentDidMount() {
        orderDetail({
            consumOrderId:this.props.match.params.id,
        }).then((res) => {
            console.log(res);
            if (res.data.code == '0') {
                let data = res.data.orders;
                this.setState({
                    clientName: data.clientName,
                    licensePlate: data.licensePlate,
                    state: data.state,
                    totalPrice: data.totalPrice,
                    id: data.id,
                    createDate: data.createDate,
                    payMethod: data.payMethod,
                    payState: data.payState,
                    projects: data.projects,
                })
            }
        }).catch((error) => { console.log(error) });

    }

    render() {
        //支付方式
        let payMethod = '';
        switch (this.state.payMethod) {
            case 0:
                payMethod = '现金';
                break;
            case 1:
                payMethod = '刷卡';
                break;
            case 2:
                payMethod = '支付宝';
                break;
            case 3:
                payMethod = '微信';
                break;
            case 4:
                payMethod = '易付宝';
                break;
            default:
                payMethod = '微信';
                break;
        }

        //服务项目
        const projects = this.state.projects.map((item, index) => {
            return <Flex key={index} className='order-list'>
                <Flex.Item className='leftLable'>{item.name}</Flex.Item>
                <Flex.Item className='rightText'>X {item.payCardTimes}</Flex.Item>
            </Flex>
        });



        return <div className="body-bac">
            <NavBar title="订单详情" />

            <Flex className="order-track-baseinfo">
                <Flex.Item className="Info">
                    <p>姓名：{this.state.clientName}</p>
                    <div>牌照号：{this.state.licensePlate}</div>
                </Flex.Item>
                <Flex.Item className="state">{this.state.state == 1 ? '已完成' : (this.state.state == 0 ? '已接车' : '已交车')}</Flex.Item>
            </Flex>
            <div className="order-track-line"></div>

            <div style={{ margin: '0 .22rem 0 .22rem', backgroundColor: '#fff' }}>
                <div className='order-list'>
                    <Flex style={{ height: '100%' }}>
                        <Flex.Item className='leftLable'>订单总额</Flex.Item>
                        <Flex.Item className='rightText' style={{ color: 'red' }}>￥{this.state.totalPrice}</Flex.Item>
                    </Flex>
                </div>
                <div className='order-list'>
                    <Flex style={{ height: '100%' }}>
                        <Flex.Item className='leftLable'>订单编号</Flex.Item>
                        <span style={{paddingRight:'.42rem'}}>{this.state.id}</span>
                    </Flex>
                </div>
                <div className='order-list'>
                    <Flex style={{ height: '100%' }}>
                        <Flex.Item className='leftLable'>订单时间</Flex.Item>
                        <Flex.Item className='rightText'>{this.state.createDate}</Flex.Item>
                    </Flex>
                </div>
                <div className='order-list'>
                    <Flex style={{ height: '100%' }}>
                        <Flex.Item className='leftLable'>支付方式</Flex.Item>
                        <Flex.Item className='rightText'>{payMethod}支付</Flex.Item>
                    </Flex>
                </div>
                <div className='order-list'>
                    <Flex style={{ height: '100%' }}>
                        <Flex.Item className='leftLable'>支付状态</Flex.Item>
                        <Flex.Item className='rightText'>{this.state.payState == 0 ? '未完成' : '已完成'}</Flex.Item>
                    </Flex>
                </div>
            </div>


            <div style={{ margin: '.21rem .22rem 0 .22rem', backgroundColor: '#fff' }}>
                <div className='order-list'>
                    <Flex style={{ height: '100%' }}>
                        <Flex.Item className='leftLable' style={{ color: '#4b4b4b' }}>服务项目</Flex.Item>
                    </Flex>
                </div>

                <div style={{ margin: '0 .42rem 0 .52rem', border: '.005rem #f0f0f0 solid' }}></div>

                <div >
                    {projects}
                </div>
            </div>

            {/* {this.state.payState == 0 && <div className='bottom-pay-button'>
                <Flex style={{ height: '100%' }}>
                    <Flex.Item className='lable'>合计:</Flex.Item>
                    <Flex.Item style={{ color: 'red' }}>￥{this.state.totalPrice}</Flex.Item>
                    <div className='pay-button'>
                        <Flex style={{ height: '100%' }}>
                            <Flex.Item style={{ textAlign: 'center', color: '#fff' }}>立即支付</Flex.Item>
                        </Flex>
                    </div>

                </Flex>
            </div>} */}


        </div>
    }
}

export default OrderDetail