import React from 'react';
import './PersonalInfo.less'
import { List, InputItem, WhiteSpace, Picker } from 'antd-mobile'
import { createForm } from 'rc-form'
import { Flex } from 'antd-mobile'
import NavBar from '../../components/NavBar'
import login from '../../img/logo.png';

class PersonalInfo extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false
        }
    }

    componentDidMount() {
        console.log(document.documentElement.clientWidth)
    }
    render() {
        const { getFieldProps } = this.props.form;
        return <div className="body-bac">
            <NavBar title="个人信息" />
            <List className="add-car-info">
                <Flex justify='center' style={{ height: '1.6rem' }}>
                    <img className='logo' src={login} style={{ width: '1.16rem' }} />
                </Flex>

                <InputItem
                    {...getFieldProps('autofocus') }
                    clear
                    placeholder="账户阅读,提醒信息"
                    autoFocus
                >昵称</InputItem>
                <InputItem
                    {...getFieldProps('focus') }
                    clear
                    placeholder="账户信息"
                    focused={this.state.focused}
                    onFocus={() => {
                        this.setState({
                            focused: false,
                        });
                    }}
                >真实姓名</InputItem>

                <Picker
                    extra="男 / 女"
                    data={[
                        { label: '男', value: '男' },
                        { label: '女', value: '女' },
                    ]} cols={1} {...getFieldProps('district3') } >
                    <List.Item>性别</List.Item>
                </Picker>

                <InputItem
                    {...getFieldProps('focus') }
                    clear
                    placeholder="1990-08-08"
                    type='number'
                    focused={this.state.focused}
                    onFocus={() => {
                        this.setState({
                            focused: false,
                        });
                    }}
                >生日</InputItem>
            </List>

            <div className="addCar-btn">保存</div>
        </div>
    }

}
const Personal = createForm()(PersonalInfo);
export default Personal