import React from 'react';
import NavBar from '../../components/NavBar'
import './MyCard.less'
import cika from '../../img/cika.png'
import zhizun from '../../img/zhizun.png'
import jinka from '../../img/jinka.png'
import {cardDetail } from '../../services/service' 
class MyCardDetail extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            card: {},
            projectInfos:[],
            name:'',
            createDate:''
        }
    }

    componentDidMount(){
        console.log(this.props.params.id)
        cardDetail({
            cardId:this.props.params.id
        }).then((res)=>{
            console.log(res)
            if(res.data.code=='0') {
                this.setState({
                    card:res.data.data,
                    name:res.data.data.service.name,
                    projectInfos:res.data.data.projectInfos,
                    createDate:res.data.data.service.createDate
                })
            }
        })
    }

    render() {
        let background
        switch (this.state.name) {
            case '1': background = cika; break;
            case '钻石卡': background = zhizun; break;
            case '3': background = jinka; break;
        }
        let detailList = this.state.projectInfos.map((item, index) => {
            return <div className="list" key={index}>
                <span className="list-name">{item.project.name}</span>
                <span className="list-time"><span className="number">{item.remaining}</span>次</span>
            </div>
        })
        return <div className="body-bac">
            <div className="nav-bar-title" style={{ marginBottom: '.3rem' }}>
                <i className="back" onClick={() => { history.back() }}></i>
                我的会员卡
                <span className="scan">添加</span>
            </div>
            <div className="membership-mycard" style={{backgroundImage:`url(${background}) `,backgroundSize:'100% 100%' }}>
                <div className="card-name">{this.state.name}</div>
                <div className="card-number">{this.state.card.cardNumber}</div>
                <div className="card-time">{this.state.createDate.slice(0,10)}</div>
            </div>
            <div className="card-detail-times" >
                <div className="title" >剩余次数</div>
                <div className="hr"></div>
                {detailList}
            </div>
        </div>
    }
}
export default MyCardDetail