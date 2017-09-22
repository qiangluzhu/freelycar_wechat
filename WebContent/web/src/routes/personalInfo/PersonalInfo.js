import React from 'react';
import './PersonalInfo.less'
import { List, InputItem, WhiteSpace, Picker } from 'antd-mobile'
import { createForm } from 'rc-form'
import { Flex } from 'antd-mobile'
import NavBar from '../../components/NavBar'
import login from '../../img/logo.png';
import { wxInfo, updateWXUser } from '../../services/user.js'
import PropTypes from 'prop-types';
class PersonalInfo extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false,
            name: '',
            headimgurl: '',
            nickName: window.localStorage.getItem('nickName')
        }
    }

    componentDidMount() {
        console.log(window.localStorage.getItem('openid'))
        wxInfo({
            openId: window.localStorage.getItem('openid')
        }).then((res) => {
            console.log(res)
            if (res.data.code == '0') {
                let data = res.data.data
                this.setState({
                    point: data.point,
                    nickName: data.wxUser.nickName,
                    name: data.wxUser.name ? data.wxUser.name : data.wxUser.nickName,
                    headimgurl: data.wxUser.headimgurl,
                    birthday: data.wxUser.birthday,
                    gender: [data.wxUser.gender]
                })
            }
        }).catch((error) => { console.log(error) });
    }

    updateInfo() {
        let myHeaders = new Headers({
            "Content-Type": "form-data",
        })
        updateWXUser({
            method: 'post',
            headers: myHeaders,
            mode: 'cors',
            cache: 'default'
        }, {

                // openId: '1',
                phone: window.localStorage.getItem('phone'),
                // phone: '110',
                birthday: this.state.birthday,
                name: this.state.name,
                gender: this.state.gender[0]

            }).then((res) => {
                console.log(res)
                this.context.router.history.push(`/center/${window.localStorage.getItem('openid')}/${window.localStorage.getItem('nickName')}/${window.localStorage.getItem('headimgurl')}`)
            })
    }

    render() {
        const { getFieldProps } = this.props.form;
        return <div className="body-bac">
            <NavBar title="个人信息" />
            <List className="add-car-info personal-info">
                <Flex justify='center' style={{ height: '1.6rem' }}>
                    <img className='logo' src={decodeURIComponent(window.localStorage.getItem('headimgurl'))} style={{ width: '1.16rem' }} />
                </Flex>

                <List.Item
                    clear
                    placeholder="账户阅读,提醒信息"
                    autoFocus
                    extra={this.state.nickName}
                    className="personInfo-gender"
                >昵称</List.Item>
                <InputItem
                    clear
                    placeholder="账户信息"
                    focused={this.state.focused}
                    value={this.state.name}
                    onFocus={() => {
                        this.setState({
                            focused: false,
                        });
                    }}
                    onChange={(value) => {
                        this.setState({
                            name: value
                        })
                    }}
                >真实姓名</InputItem>

                <Picker
                    extra="男 / 女"
                    data={[
                        { label: '男', value: '男' },
                        { label: '女', value: '女' },
                    ]} cols={1}
                    value={this.state.gender}
                    onChange={(value) => {
                        this.setState({
                            gender: value
                        })
                    }}
                >
                    <List.Item style={{ borderBottom: '1px solid #efefef' }} className={'personInfo-gender'}>性别</List.Item>
                </Picker>

                <InputItem
                    clear
                    placeholder="1990-08-08"
                    focused={this.state.focused}
                    value={this.state.birthday ? this.state.birthday.slice(0, 10) : this.state.birthday}
                    onFocus={() => {
                        this.setState({
                            focused: false,
                        });
                    }}
                    onChange={(value) => {
                        this.setState({
                            birthday: value
                        })
                    }}
                >生日</InputItem>
            </List>

            <div className="addCar-btn" onClick={() => { this.updateInfo() }}>保存</div>
        </div>
    }

}
PersonalInfo.contextTypes = {
    router: PropTypes.object.isRequired
}
const Personal = createForm()(PersonalInfo);
export default Personal
