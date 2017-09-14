import React from 'react';
import './login.less'
import { Flex } from 'antd-mobile'
import login from '../../img/logo.png';
import shutdown from '../../img/shutdown.png';
import phone from '../../img/phone.png';
import password from '../../img/password.png';
import button_login from '../../img/button_login.png';
import { verification, verifySmsCode } from '../../services/sms.js'
import PropTypes from 'prop-types';

class Login extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            allowSend: true,
            wait: 60
        }
    }

    componentDidMount() {

    }

    sendCode() {
        let myHeaders = new Headers({
            "Content-Type": "form-data",
        })
        if (this.state.allowSend) {
            verification({
                method: 'post',
                headers: myHeaders,
                mode: 'cors',
                cache: 'default'
            }, {
                    phone: this.state.phone
                }).then((res) => {
                    if (res.data.code == '0') {
                        this.setState({
                            allowSend: false
                        })
                        this.timer = setInterval(() => {
                            let wait = this.state.wait
                            this.setState({
                                wait: wait - 1
                            })
                            if (this.state.wait < 1) {
                                this.setState({
                                    allowSend: true,
                                    wait: 60
                                })
                                clearInterval(this.timer)
                            }
                        }, 1000)
                    }
                }).catch((error) => {
                    console.log(error)
                })
        }
    }

    componentWillUnmount() {
        clearInterval(this.timer)
    }

    Login() {
        // const {openid, headimgurl,nickname} = this.context.router.params;
        let myHeaders = new Headers({
            "Content-Type": "form-data",
        })

        verifySmsCode({
            method: 'post',
            headers: myHeaders,
            mode: 'cors',
            cache: 'default'
        }, {
                openId: this.props.match.params.openid,
                phone: this.state.phone,
                smscode: this.state.smscode,
                headimgurl: this.props.match.params.headimgurl,
                nickName: this.props.match.params.nickname
            }).then((res) => {
                console.log(res)
                if (res.data.client.phone) {
                    console.log('注册成功')
                    window.localStorage.setItem('headimgurl', this.props.match.params.headimgurl)
                    window.localStorage.setItem('nickName', this.props.match.params.nickname)
                    window.localStorage.setItem('phone', res.data.client.phone)
                    window.localStorage.getItem('openid', this.props.match.params.openid)
                    window.localStorage.setItem('clientId', res.data.client.id)
                    this.context.router.history.push('/personalInfo')
                }
            })
    }
    render() {
        return <div className='loginbg'>
            <div className='panel' >
                <Flex justify='center'>
                    <img className='logo' src={login} />
                </Flex>
                <img className='shutdown' src={shutdown} />
                <Flex justify='center'  >
                    <div className='input-up'>
                        <img src={phone} style={{ width: '.3rem', marginLeft: '.18rem', marginRight: '0.5rem', verticalAlign: 'middle' }} />
                        <input className='no-border' style={{ width: '4.3rem' }} onChange={(e) => { this.setState({ phone: e.target.value }) }} placeholder="请输入手机号码" />
                    </div>
                </Flex>
                <Flex justify='center'>
                    <div className='input-up'>
                        <img src={password} style={{ width: '.34rem', marginLeft: '.18rem', marginRight: '0.48rem', verticalAlign: 'middle' }} />
                        <input className='no-border' placeholder="请输入验证码" style={{ display: 'inner-block', width: '2rem' }} onChange={(e) => { this.setState({ smscode: e.target.value }) }} />
                        <div style={{ display: 'inline-block', color: '#5b87e5', verticalAlign: 'middle', paddingLeft: '.33rem', borderLeft: '1px solid #e8e8e8' }} onClick={() => { this.sendCode() }}>{this.state.allowSend ? '获取短信验证码' : `${this.state.wait}s后重新发送`}</div>
                    </div>
                </Flex>
                <div style={{ textAlign: 'center', color: "#cdcdcd", lineHeight: '0.98rem', fontSize: '.18rem' }}>
                    为了防止用户信息被盗,请使用本机号码
                </div>
                <div style={{ height: '0.98rem', textAlign: 'center', color: "#fff", borderRadius: '10rem', lineHeight: '0.98rem', margin: '0 .48rem 0 .48rem', background: '#5b87e5' }} onClick={() => { this.Login() }}>
                    注 册
                </div>
            </div>
        </div>
    }
}

export default Login
Login.contextTypes = {
    router: PropTypes.object.isRequired
}