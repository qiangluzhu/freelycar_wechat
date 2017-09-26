import React from 'react';
import './Personalcenter.less'
import { Flex, Modal } from 'antd-mobile'
import membershipcard from '../../img/membershipcard_icon@2x.png'
import more_arrow from '../../img/more_arrow.png'
import Vehiclemanagement_icon from '../../img/Vehiclemanagement_icon.png'
import order_icon from '../../img/order_icon.png'
import Contactxiaoyi_icon from '../../img/Contactxiaoyi_icon.png'
import avatar from '../../assets/yay.jpg'
import banner from '../../img/member_banner.png'
import { wxInfo, userDetail, modifyCarInfo, logout } from '../../services/user.js'
import PropTypes from 'prop-types';

const alert = Modal.alert
class Personalcenter extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            point: 0,
            name: '',
            headimgurl: this.props.match.params.headimgurl,
            tickets: [],
            card: [],
            cars: [],
            order: [],
            projectInfos: [],
            modal1: false
        }
    }

    componentDidMount() {
        wxInfo({
            openId: this.props.match.params.openid
        }).then((res) => {
            console.log(res)
            if (res.data.code == '0') {
                let data = res.data.data
                this.setState({
                    point: data.point,
                    name: data.name ? data.name : data.nickName,
                    headimgurl: data.headimgurl
                })
            }
            userDetail({
                clientId: window.localStorage.getItem('clientId')
            }).then((res) => {
                console.log(res)
                if (res.data.code == '0') {
                    let data = res.data
                    this.setState({
                        card: data.client.cards.length > 0 ? data.client.cards[0] : [],
                        cars: data.client.cars,
                        order: data.orders.length > 0 ? data.orders[0] : [],
                        tickets: data.client.tickets,
                        projectInfos: data.client.cards.length > 0 ? data.client.cards[0].projectInfos.slice(0, 3) : []
                    })
                    window.localStorage.setItem('isMember', data.client.isMember)
                    window.localStorage.setItem('headimgurl', this.props.match.params.headimgurl)
                    window.localStorage.setItem('nickName', this.props.match.params.nickname)
                    window.localStorage.setItem('phone', data.client.phone)
                    window.localStorage.setItem('openid', this.props.match.params.openid)
                    window.localStorage.setItem('clientId', data.client.id)
                }
            }).catch((error) => {
                console.log(error)
            })
        }).catch((error) => { console.log(error) })
        // modifyCarInfo({
        //     carId: '8',
        //     car: {
        //         createDate: '2017-08-11 16:01:46',
        //         insuranceAmount: 0,
        //         lastMiles: 12,
        //         licensePlate: ' 苏E06E8V',
        //         miles: 0,
        //         newCar: false
        //     }
        // }).then((res) => {
        //     console.log(res)
        // })

    }

    logout = () => {
        const alertInstance = alert('', '确定退出登录吗?', [
            {
                text: '是', onPress: () => logout({
                    openId: this.props.match.params.openid
                }).then((res) => {
                    this.context.router.history.push(`/login/${window.localStorage.getItem('openid')}/${window.localStorage.getItem('nickName')}/${window.localStorage.getItem('headimgurl')}`)
                })
            },
            {
                text: '否', onPress: () => console.log('cancel'), style: 'default'
            },
        ]);
        setTimeout(() => {
            alertInstance.close();
        }, 2000);

    }

    render() {
        let programs = this.state.projectInfos.map((item, index) => {
            return <Flex.Item key={index}>
                <Flex direction="column" justify="center">
                    <Flex.Item className="vip-card-program">{item.project.name}</Flex.Item>
                    <Flex.Item className="program-time">{item.remaining}</Flex.Item>
                </Flex>
            </Flex.Item>
        });
        let totalPrice = 0;
        // console.log(this.state.order.projects)
        if (this.state.order.projects) {
            for (let item of this.state.order.projects) {
                totalPrice = totalPrice + item.presentPrice + item.pricePerUnit * item.referWorkTime
            }
        }


        return <div className="body-bac" style={{ paddingTop: '0' }}>
            <div className="top-gradient">
            </div>
            <div className="clear"><div className="center-login-out" onClick={() => {
                this.logout()
            }}></div><a href="tel:18512391863" className="center-line-phone"></a></div>
            <Flex justify="between" align='start' direction="column" className="person-info">
                <Flex justify="between" align='start' style={{ height: '1.2rem' }} >
                    <div className="avatar"><img src={decodeURIComponent(this.props.match.params.headimgurl)} alt="" /></div>
                    <Flex.Item style={{ marginLeft: '.3rem' }} direction="column">
                        <div className="info-name">{this.state.name}</div>
                        <Flex justify="between" onClick={() => { this.context.router.history.push('/personalInfo') }}>
                            <Flex.Item>个人信息 ></Flex.Item>
                        </Flex>
                    </Flex.Item>
                </Flex>
                <Flex justify="between" style={{ width: '100%' }}>
                    <Flex direction="column" justify="center" align="center" style={{ width: '50%' }} onClick={() => { this.context.router.history.push('/membership/myfavour') }}>
                        <div style={{ fontSize: '.36rem', color: '#37cedc' }}>{this.state.tickets.length}<span style={{ fontSize: '.16rem' }}>个</span></div>
                        <div style={{ fontSize: '.22rem', lineHeight: '.35rem', color: '#8e8e8e', height: '.35rem' }}>优惠</div>
                    </Flex>
                    <Flex direction="column" justify="center" align="center" style={{ width: '50%' }} onClick={() => { this.context.router.history.push('/membership/mypoints') }}>
                        <div style={{ fontSize: '.36rem', color: '#37cedc' }}>{this.state.point}<span style={{ fontSize: '.16rem' }}>分</span></div>
                        <div style={{ fontSize: '.22rem', lineHeight: '.35rem', color: '#8e8e8e', height: '.35rem' }}>积分</div>
                    </Flex>
                </Flex>
            </Flex>
            {this.state.card.length == 0 && <div className="center-banner" onClick={() => { 
                //this.context.router.history.push('/addCard')
                history.pushState(null,null,`/freelycar_wechat/index.html#/addCard`);
                window.location.reload();
                }}><img src={banner} alt="" /></div>}
            {this.state.card.length != 0 && <div className="vip-gold-card" style={{ marginTop: '.2rem' }} onClick={() => {
                this.context.router.history.push('/membership/mycard')
            }}>
                <Flex className="vip-card-line1">
                    <div className="icon-close"><img src={membershipcard} alt="" /></div>
                    <Flex.Item className="vip-card-name">{this.state.card.service ? this.state.card.service.name : ''}</Flex.Item>
                    <Flex.Item className="vip-card-more" style={{ color: '#8e8e8e' }} >全部<img src={more_arrow} style={{ marginLeft: '.2rem' }} alt="" /></Flex.Item>
                </Flex>
                {this.state.projectInfos.length > 0 && <Flex>
                    {programs}
                </Flex>}
            </div>}
            <Flex className="center-line-box" onClick={() => {
                if (this.state.cars.length > 0) {
                    this.context.router.history.push('/carInfo')
                } else { this.context.router.history.push('/addcar/0') }
            }}>
                <div className="center-icon1"><img src={Vehiclemanagement_icon} alt="" /></div>
                <p>爱车管理</p>
                <Flex.Item className="vip-card-more"><img src={more_arrow} alt="" /></Flex.Item>
            </Flex>

            <Flex className="center-line-box" style={{ marginBottom: '0' }} onClick={() => { this.context.router.history.push('/serviceCard') }}>
                <div className="center-icon2"><img src={order_icon} alt="" /></div>
                <p>订单</p>
                <Flex.Item className="vip-card-more">全部<img style={{ marginLeft: '.2rem' }} src={more_arrow} alt="" /></Flex.Item>
            </Flex>
            {this.state.order.projects && <Flex className="center-listItem" direction="column">
                <Flex style={{ width: '100%', height: '.4rem', fontSize: '.24rem', color: '#4b4b4b' }}>
                    <i className="circle"></i>
                    <p>{this.state.order.projects[0] ? (this.state.order.projects[0].name) : ''} {this.state.order.projects.length > 1 ? <span style={{ color: '#1e1e1e', opacity: '0.5' }}>...</span> : ''}</p>
                    <Flex.Item className="finish-state">{this.state.order.state == 1 ? '已接车' : (this.state.order.state == 2 ? '已完工' : '已交车')}</Flex.Item>
                </Flex>
                <Flex style={{ width: '100%', height: '.4rem', fontSize: '.18rem', color: '#8e8e8e' }}>
                    <i className="circle2"></i>
                    <p>{this.state.order.createDate}</p>
                    <Flex.Item className="total-price">￥{totalPrice}</Flex.Item >
                </Flex>
            </Flex>}
        </div>
    }
}

export default Personalcenter
Personalcenter.contextTypes = {
    router: PropTypes.object.isRequired
}