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
import { carDetail,modifyCarInfo} from '../../services/user.js'
import { browserHistory } from 'dva/router'
import cityJson from '../../data/city.json';
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
            //compulsoryInsurance: '',//强制险
            commercialInsurance:'',//商业险
            carPeopleName:'',
            peopleIdNumber:'',//身份证
        }
    }

    componentDidMount() {
        carDetail({
            carId: this.props.params.carId,
        }).then((res) => {
            console.log(res);
            let  dt = res.data.data;
            let insuranceCity = dt.insuranceCity.split(',');
            this.setState({
                carPeopleName:res.data.clientName,
                peopleIdNumber:res.data.idNumber,
                insuredCity:insuranceCity,
                insuredCompany:[dt.insuranceCompany],
                //commercialInsurance:dt.insuranceEndtime,
                commercialInsurance:moment(dt.insuranceEndtime, "YYYY-MM-DD"),

            });
        }).catch((error) => { console.log(error) });
        
    }


    modifyCarInfo=()=>{
        modifyCarInfo({
            clientId: this.props.params.clientId,
            id:this.props.params.carId,
            insuranceCompany:this.state.insuredCompany[0],
            insuranceCity:this.state.insuredCity[0]+','+this.state.insuredCity[1],
            insuranceEndtime:this.state.commercialInsurance.format('YYYY-MM-DD')
        }).then((res) => {
            if(res.data.code=='0'){
                browserHistory.push('/carInfo')
            }
        }).catch((error) => { console.log(error) });
    }

    render() {

        const insuredCompany = [
            { label: "中国人保车险", value: "中国人保车险" },
            { label: "平安车险", value: "平安车险" },
            { label: "太平洋车险", value: "太平洋车险" },
            { label: "中华联合车险", value: "中华联合车险" },
            { label: "大地车险", value: "大地车险" },
            { label: "天安车险", value: "天安车险" },
            { label: "永安车险", value: "永安车险" },
            { label: "阳光车险", value: "阳光车险" },
            { label: "安邦车险", value: "安邦车险" },
            { label: "太平车险", value: "太平车险" },
            { label: "其他", value: "其他" },
        ];

        const insuredCity = cityJson;

        return <div className="body-bac">
            <NavBar title="爱车信息" />

            <List style={{ backgroundColor: 'white' }} className="picker-list container">
                <InputItem
                    clear
                    placeholder="填写真实姓名"
                    autoFocus
                    value={this.state.carPeopleName}
                    disabled
                >真实姓名</InputItem>

                <InputItem
                    clear
                    placeholder="填写身份证号"
                    autoFocus
                    disabled
                    value={this.state.peopleIdNumber}
                >身份证</InputItem>

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
                    cols={2}
                    onOk={e => this.setState({ insuredCity: e })}
                    onDismiss={e => console.log('dismiss', e)}
                    value={this.state.insuredCity}
                >
                    <List.Item arrow="horizontal">投保城市</List.Item>
                </Picker>

                <DatePicker
                    mode="date"
                    title="选择日期"
                    value = {this.state.commercialInsurance}        
                    onChange={(e)=>{this.setState({commercialInsurance:e})}}
                >
                    <List.Item arrow="horizontal">保险到期时间</List.Item>
                </DatePicker>
            </List>
            
            <div style={{height:'1rem',width:'100%',backgroundColor:'#5a88e5',textAlign:'center',lineHeight:'1rem',color:'#fff',marginTop:'1.89rem'}}
            onClick={()=>{this.modifyCarInfo()}}
            >保存</div>
        </div>
    }

}
export default Insurance