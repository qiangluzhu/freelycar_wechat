import React from 'react'
import { Flex } from 'antd-mobile'
import './addCard.less'
import NavBar from '../../components/NavBar'
import gold_card from '../../img/gold_card.png'
import wgold_card from '../../img/wgold_card.png'
import diamonds_card from '../../img/diamonds_card.png'
import extreme_card from '../../img/extreme_card.png'
import times_card from '../../img/times_card.png'
import { getCardList } from '../../services/service.js'


class OrderDetail extends React.Component {
    constructor(props) {
        super(props)
        this.state = {
            card: [
                { name: '次卡', src: times_card },
                { name: '金卡', src: gold_card },
                { name: '白金卡', src: wgold_card },
                { name: '钻石卡', src: diamonds_card },
                { name: '至尊卡', src: extreme_card }
            ],
            arrowIndex: 0,
            arrowName: '次卡',
            services: [],
        }
    }
    componentDidMount() {
        let mySwiper3 = new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: false,
            slidesPerView: 4,
            slidesPerGroup: 1,
            centeredSlides: false
            // 如果需要分页器
        });

        getCardList({
            page: 1,
            number: 22
        }).then((res) => {
            if (res.data.code == '0') {
                this.setState({
                    services: res.data.data,
                })
            }
        }).catch((error) => { console.log(error) });



    }

    onHanleArrowDisplay = (name, index) => {
        this.setState({
            arrowIndex: index,
            arrowName: name,
        });
    }
    render() {

        let cards = this.state.card;

        let cardList = cards.map((item, index) => {
            return <div key={index} className="card-item swiper-slide" onClick={() => { this.onHanleArrowDisplay(item.name, index) }}>
                <img src={item.src} />
                <div className='label'>{item.name}</div>
                <div className={`arrow ${this.state.arrowIndex == index ? '' : 'hidden'}`}></div>
            </div>

        });
        let serviceItem = this.state.services.map((item, index) => {
            let service = item
            if (item.name == this.state.arrowName) {
                let proInfos = service.projectInfos;
                let item = proInfos.map((item1, index1) => {
                    return <div key={index+''+index1} className='vip-service-item'>
                        <div className='label-left'>{item1.project.name}</div>
                        <div className='label-right'>
                            <span className='count'>{item1.times}</span>
                            <span className='unit'>次</span>
                        </div>
                    </div>
                });
                return item;
            }
        });

        return (<div>
            <NavBar title={'会员卡添加'}></NavBar>
            <div className="swiper-container all-card-list" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    {cardList}
                </div>
            </div>

            <div className='vip-service'>
                <div className='vip-service-div'>
                    <div className='vip-service-header'>会员专项</div>
                    {serviceItem}
                </div>
            </div>


            <div className='bottom-pay-button'>
                <Flex style={{ height: '100%' }}>
                    <Flex.Item className='lable'>合计:</Flex.Item>
                    <Flex.Item style={{ color: 'red' }}><span style={{ fontSize: '12px' }}>￥</span><span style={{ fontSize: '16px' }}>999</span></Flex.Item>
                    <div className='pay-button'>
                        <Flex style={{ height: '100%' }}>
                            <Flex.Item style={{ textAlign: 'center', color: '#fff' }}>立即购买</Flex.Item>
                        </Flex>
                    </div>
                </Flex>
            </div>

        </div>
        );
    }
}

export default OrderDetail
