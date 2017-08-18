import React from 'react';
import './Insurance.less'
import { List, InputItem, WhiteSpace, Picker, Flex, Icon, Switch, DatePicker } from 'antd-mobile'
import moment from 'moment';
import { createForm } from 'rc-form'
import NavBar from '../../components/NavBar'
import car_icon from '../../img/car_icon.jpg'
import insurance from '../../img/insurance.png'
import annualInspection from '../../img/annualInspection.png'
import more_arrow from '../../img/more_arrow.png'

const Item = List.Item;
const Brief = Item.Brief;

const minDate = moment('2015-08-06 +0800', 'YYYY-MM-DD Z').utcOffset(8);
const today = moment();
const maxDate = moment('2016-12-03 +0800', 'YYYY-MM-DD Z').utcOffset(8);

class Insurance extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            insuredCompany: '',
            insuredCity: '',
            compulsoryInsurance: '',//强制险
            commercialInsurance:'',//商业险
        }
    }

    componentDidMount() {
    }


    render() {

        const insuredCompany = [
            { label: "太平洋保险", value: "太平洋保险1" },
            { label: "人寿保险", value: "人寿保险1" }
        ];

        const insuredCity = [
            { label: "南京", value: "南京" },
            { label: "上海", value: "上海" }
        ];

        return <div className="body-bac">
            <NavBar title="爱车信息" />

            <List style={{ backgroundColor: 'white' }} className="picker-list">
                <InputItem
                    clear
                    placeholder="填写真实姓名"
                    autoFocus
                >车主姓名</InputItem>

                <InputItem
                    clear
                    placeholder="填写身份证号"
                    autoFocus
                >身份证号</InputItem>

                <Picker extra="填写投保公司"
                    data={insuredCompany}
                    onOk={e => this.setState({ insuredCompany: e })}
                    cols={1}
                    onDismiss={e => console.log('dismiss', e)}
                    value={this.state.insuredCompany}
                >
                    <List.Item arrow="horizontal">投保公司</List.Item>
                </Picker>
                <Picker extra="填写投保城市"
                    data={insuredCity}
                    cols={1}
                    onOk={e => this.setState({ insuredCity: e })}
                    onDismiss={e => console.log('dismiss', e)}
                    value={this.state.insuredCity}
                >
                    <List.Item arrow="horizontal">投保城市</List.Item>
                </Picker>
                <DatePicker
                    mode="date"
                    title="选择日期"
                    minDate={minDate}
                    maxDate={maxDate}
                    value = {this.state.compulsoryInsurance}        
                    onChange={(e)=>{this.setState({compulsoryInsurance:e})}}
                >
                    <List.Item arrow="horizontal">交强险到期时间</List.Item>
                </DatePicker>

                <DatePicker
                    mode="date"
                    title="选择日期"
                    minDate={minDate}
                    maxDate={maxDate}
                    value = {this.state.commercialInsurance}        
                    onChange={(e)=>{this.setState({commercialInsurance:e})}}
                >
                    <List.Item arrow="horizontal">商业险到期时间</List.Item>
                </DatePicker>
            </List>

        </div>
    }

}
export default Insurance