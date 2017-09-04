import React from 'react';
import './Personalcenter.less'
import { Flex } from 'antd-mobile'
import membershipcard from '../../img/membershipcard_icon@2x.png'
import more_arrow from '../../img/more_arrow.png'
import Vehiclemanagement_icon from '../../img/Vehiclemanagement_icon.png'
import order_icon from '../../img/order_icon.png'
import Contactxiaoyi_icon from '../../img/Contactxiaoyi_icon.png'
import avatar from '../../assets/yay.jpg'
import banner from '../../img/member_banner.png'
import { wxInfo,userDetail } from '../../services/user.js'
import {browserHistory} from 'dva/router'
class Personalcenter extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            point:0,
            name:'',
            headimgurl:'',
        }
    }

    componentDidMount() {
        wxInfo({
            openId: '11'
        }).then((res) => {
            console.log(res)
            if (res.data.code == '0') {
                let data = res.data.data
                this.setState({
                    point: data.point,
                    name: data.name ? data.name : data.nickName,
                    headimgurl:data.headimgurl
                })
            }
        }).catch((error)=>{console.log(error)})

        userDetail({
            clientId:'10'
        }).then((res)=>{
            console.log(res)
            if(res.data.code == '0') {
                let data = res.data.data
                this.setState({
                    card:data.cards
                })
            }
        }).catch((error)=>{
            console.log(error)
        })
    } 
    render() {
        return <div className="body-bac">
            <div className="top-gradient">
            </div>
            <div className="clear"><div className="center-login-out"></div><a href="tel:18512391863" className="center-line-phone"></a></div>
            <Flex justify="between" align='start' direction="column" className="person-info">
                <Flex justify="between" align='start' style={{ height: '1.2rem' }} >
                    <div className="avatar"><img src={avatar} alt="" /></div>
                    <Flex.Item style={{ marginLeft: '.3rem' }} direction="column">
                        <div className="info-name">Anan托马斯</div>
                        <Flex justify="between">
                            <Flex.Item>个人信息 ></Flex.Item>
                        </Flex>
                    </Flex.Item>
                </Flex>
                <Flex justify="between" style={{ width: '100%' }}>
                    <Flex direction="column" justify="center" align="center" style={{ width: '50%' }}>
                        <div style={{ fontSize: '.36rem', color: '#37cedc' }}>3<span style={{ fontSize: '.16rem' }}>个</span></div>
                        <div style={{ fontSize: '.22rem', lineHeight: '.35rem', color: '#8e8e8e' }}>优惠</div>
                    </Flex>
                    <Flex direction="column" justify="center" align="center" style={{ width: '50%' }}>
                        <div style={{ fontSize: '.36rem', color: '#37cedc' }}>2000<span style={{ fontSize: '.16rem' }}>分</span></div>
                        <div style={{ fontSize: '.22rem', lineHeight: '.35rem', color: '#8e8e8e' }}>积分</div>
                    </Flex>
                </Flex>
            </Flex>

            <div className="center-banner"><img src={banner} alt="" /></div>
            <Flex className="center-line-box" onClick={()=>{browserHistory.push('/addcar')}}>
                <div className="center-icon1"><img src={Vehiclemanagement_icon} alt="" /></div>
                <p>爱车管理</p>
                <Flex.Item className="vip-card-more"><img src={more_arrow} alt="" /></Flex.Item>
            </Flex>
            <div className="vip-gold-card">
                <Flex className="vip-card-line1">
                    <div className="icon-close"><img src={membershipcard} alt="" /></div>
                    <Flex.Item className="vip-card-name">会员金卡</Flex.Item>
                    <Flex.Item className="vip-card-more" style={{ color: '#8e8e8e' }} >全部会员卡管理<img src={more_arrow} style={{ marginLeft: '.2rem' }} alt="" /></Flex.Item>
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
        </div>
    }
}

export default Personalcenter