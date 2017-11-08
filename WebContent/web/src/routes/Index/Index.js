import React from 'react';
import {Flex} from 'antd-mobile'
import './index.less'
import TabBar from '../../components/TabBar.js'
class Index extends React.Component {

    constructor(props) {
        super(props)
        this.state = {

        }
    }

    componentDidMount() {
        let mySwiper4 = new Swiper(this.swiperID, {
            direction: 'horizontal',
            loop: true,
            // 如果需要分页器
            pagination: '.swiper-pagination',
        });
    }

    render() {
        return <div>
            <div className="swiper-container" ref={self => this.swiperID = self}>
                <div className="swiper-wrapper">
                    <div className="swiper-slide  banner-img "><img src={require('../../img/index_banner.jpg')} alt="" /></div>
                </div>
                <div className="swiper-pagination circle-color"></div>
            </div>

            <Flex direction="row" justify="center" className="index-addcar-btn" >
                <div className="index-addcar-icon"><img src={require('../../img/icon_car.png')}/></div>
                <div className="index-addcar-font">添 加 爱 车 <img src={require('../../img/discount.png')}/> 不 停</div>
            </Flex>

            <Flex className="index-block-box">
                <div className="icon-sale"><img src={require('../../img/icon_sale.png')}/></div>
                <div className="index-block-icon"><img src={require('../../img/icon_card.png')}/></div>
                <div>
                    <div style={{fontSize:'.32rem',marginBottom:'.2rem'}}>办理会员</div>
                    <div style={{fontSize:'.22rem'}}>多重好礼享不停！</div>
                </div>
            </Flex>

            <Flex className="index-block-box">
                <div className="index-block-icon"><img src={require('../../img/icon_shop.png')}/></div>
                <div>
                    <div style={{fontSize:'.32rem',marginBottom:'.2rem'}}>门店优惠</div>
                    <div style={{fontSize:'.22rem'}}>门店优惠多多，洗车只需20元！速来抢购！</div>
                </div>
            </Flex>

            <Flex>
                <Flex style={{marginRight:'.2rem',flex:1}} className="index-row-block-box">
                    <div className="index-block-icon">
                        <img src={require('../../img/icon_chexian.png')}/>
                    </div>
                    <div>
                        车险询价
                    </div>
                </Flex>
                <Flex style={{flex:1}}  className="index-row-block-box">          
                    <div className="index-block-icon">
                        <img src={require('../../img/icon_guzhi.png')}/>
                    </div>
                    <div>
                        爱车估值
                    </div>
                </Flex>
            </Flex>
            <TabBar nowTab={1}/> 
        </div>
    }

}

export default Index