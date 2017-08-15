import React from 'react';
import NavBar from '../../components/NavBar'
import './MyCard.less'
import cika from '../../img/cika.png'
import zhizun from '../../img/zhizun.png'
import jinka from '../../img/jinka.png'
class MyCardDetail extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            card: {
                name: '会员次卡', number: '46516514465', time: '2017.05.23', id: '1', items: [{ name: '洗车', times: '10' },
                { name: '精致洗车', times: '20' }, { name: '内饰除菌Spa', times: '1' }, { name: '机器打蜡', times: '2' }]
            }
        }
    }

    render() {
        let background
        switch (this.state.card.id) {
            case '1': background = cika; break;
            case '2': background = zhizun; break;
            case '3': background = jinka; break;
        }
        let detailList = this.state.card.items.map((item, index) => {
            return <div className="list" key={index}>
                <span className="list-name">{item.name}</span>
                <span className="list-time"><span className="number">{item.times}</span>次</span>
            </div>
        })
        return <div>
            <div className="nav-bar-title" style={{ marginBottom: '.3rem' }}>
                <i className="back" onClick={() => { history.back() }}></i>
                我的会员卡
                <span className="scan">添加</span>
            </div>
            <div className="membership-mycard" style={{ background: `url(${background})`, backgroundSize: '100% 100%' }}>
                <div className="card-name">{this.state.card.name}</div>
                <div className="card-number">{this.state.card.number}</div>
                <div className="card-time">{this.state.card.time}</div>
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