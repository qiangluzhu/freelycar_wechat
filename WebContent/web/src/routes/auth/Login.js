import React from 'react';
import './login.less'
import { Flex } from 'antd-mobile'
import login from '../../img/logo.png';
import shutdown from '../../img/shutdown.png';
import phone from '../../img/phone.png';
import password from '../../img/password.png';
import button_login from '../../img/button_login.png';
class Login extends React.Component {

    constructor(props) {
        super(props)
        this.state = {

        }
    }

    render() {
        return <div className='loginbg'>
            <div className='panel' >
                <Flex justify='center'>
                    <img className='logo' src={login} />
                </Flex>

                <img className='shutdown' src={shutdown} />

                <Flex justify='center'>
                    <div className='input-up'>
                        <img src={phone} style={{width:'.3rem',marginTop:'.32rem',marginLeft:'.18rem',marginRight:'0.5rem'}}/>
                        <input className='no-border' placeholder="请输入手机号码" />
                    </div>
                </Flex>
                <Flex justify='center'>
                    <div className='input-up'>
                        <img src={password} style={{width:'.34rem',marginTop:'.32rem',marginLeft:'.18rem',marginRight:'0.48rem'}}/>
                        <input className='no-border' placeholder="请输入验证码" style={{display:'inner-block'}} />
                        <div style={{color:'#5b87e5',right:'0',top:'.5rem',position:'absolute'}}>获取短信验证码</div>
                    </div>
                </Flex>

                <div  style={{textAlign:'center',color:"#cdcdcd",lineHeight:'0.98rem'}}>
                     为了防止用户信息被盗,请使用本机号码
                </div>

                <div style={{height:'0.98rem',textAlign:'center',color:"#fff",borderRadius:'10rem',lineHeight:'0.98rem',margin:'0 .48rem 0 .48rem',background:'#5b87e5'}}>
                     登 录
                </div>
            </div>
        </div>
    }
}

export default Login