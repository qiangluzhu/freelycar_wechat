import React from 'react';
import './Personalcenter.less'
import { Flex } from 'antd-mobile'
import membershipcard from '../../img/membershipcard_icon@2x.png'
import more_arrow from '../../img/more_arrow.png'
import Vehiclemanagement_icon from '../../img/Vehiclemanagement_icon.png'
import order_icon from '../../img/order_icon.png'
import Contactxiaoyi_icon from '../../img/Contactxiaoyi_icon.png'
import avatar from '../../assets/yay.jpg'
class Personalcenter extends React.Component {

    constructor(props) {
        super(props)
        this.state = {

        }
    }

    render() {
        return <div className="body-bac">
            <div className="top-gradient">
            </div>
            <Flex justify="between" align='center' direction="row" className="person-info">
                <div className="avatar"><img src={avatar} alt="" /></div>
                <Flex.Item style={{ marginLeft: '.3rem' }} direction="column">
                    <div className="info-name">Anan托马斯</div>
                    <Flex justify="between">
                        <Flex.Item>个人信息 ></Flex.Item>
                    </Flex>
                </Flex.Item>
            </Flex>
            <div className="vip-gold-card">
                <Flex className="vip-card-line1">
                    <div className="icon-close"><img src={membershipcard} alt="" /></div>
                    <Flex.Item className="vip-card-name">会员金卡</Flex.Item>
                    <Flex.Item className="vip-card-more"><img src={more_arrow} alt="" /></Flex.Item>
                </Flex>
                <Flex>
                    <Flex.Item>
                        <Flex direction="column" justify="center">
                            <Flex.Item className="vip-card-program">普洗</Flex.Item>
                            <Flex.Item className="program-time">20</Flex.Item>
                        </Flex>
                    </Flex.Item>
                    <Flex.Item>
                        <Flex direction="column" justify="center">
                            <Flex.Item className="vip-card-program">精洗</Flex.Item>
                            <Flex.Item className="program-time">2</Flex.Item>
                        </Flex>
                    </Flex.Item>
                    <Flex.Item>
                        <Flex direction="column" justify="center">
                            <Flex.Item className="vip-card-program">除菌SPA</Flex.Item>
                            <Flex.Item className="program-time">1</Flex.Item>
                        </Flex>
                    </Flex.Item>
                </Flex>
            </div>
            <Flex className="center-line-box">
                <div className="center-icon1"><img src={Vehiclemanagement_icon} alt="" /></div>
                <p>总积分</p>
                <Flex.Item className="vip-card-more">2000积分<img style={{ marginLeft: '.2rem' }} src={more_arrow} alt="" /></Flex.Item>
            </Flex>
            <Flex className="center-line-box">
                <div className="center-icon1"><img src={Vehiclemanagement_icon} alt="" /></div>
                <p>优惠券</p>
                <Flex.Item className="vip-card-more">3张<img style={{ marginLeft: '.2rem' }} src={more_arrow} alt="" /></Flex.Item>
            </Flex>
            <Flex className="center-line-box">
                <div className="center-icon1"><img src={Vehiclemanagement_icon} alt="" /></div>
                <p>爱车管理</p>
                <Flex.Item className="vip-card-more"><img src={more_arrow} alt="" /></Flex.Item>
            </Flex>
            <Flex className="center-line-box" style={{ marginBottom: '0' }}>
                <div className="center-icon2"><img src={order_icon} alt="" /></div>
                <p>订单</p>
                <Flex.Item className="vip-card-more">全部订单 订单详情<img style={{ marginLeft: '.2rem' }} src={more_arrow} alt="" /></Flex.Item>
            </Flex>
            <Flex className="center-listItem" direction="column">
                <Flex style={{ width: '100%', height: '.4rem', fontSize: '.24rem', color: '#4b4b4b' }}>
                    <i className="circle"></i>
                    <p>内饰除菌SPA</p>
                    <Flex.Item className="finish-state">已完成</Flex.Item>
                </Flex>
                <Flex style={{ width: '100%', height: '.4rem', fontSize: '.18rem', color: '#8e8e8e' }}>
                    <i className="circle2"></i>
                    <p>2017/07/25 14:23</p>
                    <Flex.Item className="total-price">￥200</Flex.Item >
                </Flex>
            </Flex>

            <a  href="tel:18512391863" style={{color:'#000'}}>
                <Flex className="center-line-box">
                    <div className="center-icon3"><img src={Contactxiaoyi_icon} alt="" /></div>
                    <p>联系小易：18512391863</p>
                </Flex>
            </a>
        </div>
    }
}

export default Personalcenter