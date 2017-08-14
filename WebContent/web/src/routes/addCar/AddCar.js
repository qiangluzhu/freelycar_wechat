import React from 'react';
import './AddCar.less'
import { List, InputItem, WhiteSpace, Picker } from 'antd-mobile'
import { createForm } from 'rc-form'
import NavBar from '../../components/NavBar'
const Item = List.Item;
class AddCar extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            focused: false,
            focused1: false,
            data: [{key:1,label:'A'},{key:2,label:'A'},{key:3,label:'A'},{key:4,label:'A'},{key:5,label:'A'}]
        }
    }

    componentDidMount() {
        console.log(document.documentElement.clientWidth)
    }
    render() {
        const { getFieldProps } = this.props.form;
        return <div className="body-bac">
            <NavBar title="添加爱车" />
            <List className="add-car-info">

                <InputItem
                    {...getFieldProps('number') }
                    type="number"
                    clear
                    placeholder="填写车牌号"
                    maxLength="5"
                    labelNumber="6"
                >
                    <div style={{ display: 'inline-block' }}>车牌号</div>
                    <div style={{ display: 'inline-block' }}>
                        <Picker
                            data={this.state.data}
                            title="选择地区"
                            onOk={e => console.log('ok', e)}

                        >
                            <List.Item arrow="down" style={{ display: 'inline-block' }}></List.Item>
                        </Picker>
                    </div>
                    <div style={{ display: 'inline-block' }}>
                        <Picker
                            data={this.state.data}
                            title="选择城市"
                            onOk={e => console.log('ok', e)}

                        >
                            <List.Item arrow="down" style={{ display: 'inline-block' }}></List.Item>
                        </Picker>
                    </div>
                </InputItem>
                <Item extra="请选择品牌车系" arrow="horizontal" onClick={() => { }}>品牌车系</Item>
                <Item extra="点击添加车型" arrow="horizontal" onClick={() => { }}>车型</Item>
            </List>
            <div className="addCar-btn">保存</div>
        </div>
    }

}
const AddCarInfo = createForm()(AddCar);
// ReactDOM.render(<AddCarInfo />, mountNode);
export default AddCarInfo