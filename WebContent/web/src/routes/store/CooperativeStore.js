import React from 'react';
import { Flex, ListView } from 'antd-mobile';
import NavBar from '../../components/NavBar'
import './CooperativeStore.less'
import { storeList } from '../../services/store'
import PropTypes from 'prop-types';
const NUM_ROWS = 10;
// let pageIndex = 1;
let index = 10;
class CooperativeStore extends React.Component {

    constructor(props) {
        super(props)
        const getSectionData = (dataBlob, sectionID) => dataBlob[sectionID];
        const getRowData = (dataBlob, sectionID, rowID) => dataBlob[rowID];
        const dataSource = new ListView.DataSource({
            rowHasChanged: (row1, row2) => row1 !== row2,
        });

        this.state = {
            dataSource: dataSource.cloneWithRows({}),
            isLoading: true,
            hasMore: true,
            pageIndex:1
        }
    }

    componentDidMount() {
        setTimeout(() => {
            this.rData = this.genData(this.state.pageIndex);
            this.setState({
                dataSource: this.state.dataSource.cloneWithRows(this.rData),
                isLoading: false,
            });
        }, 600);
    }

    genData = (pIndex = 1) => {
        // const dataBlob = {};
        // for (let i = 0; i < NUM_ROWS; i++) {
        //     const ii = (pIndex * NUM_ROWS) + i;
        //     dataBlob[`${ii}`] = `row - ${ii}`;
        // }
        // console.log(dataBlob)
        // return dataBlob;
        let dataBlob = {} 
        storeList({
            page: pIndex,
            number: 10
        }).then((res) => {
            if (res.data.code == '0') {
                console.log(res)
                for (let i = 0; i <res.data.data.length; i++) {
                    const ii = (pIndex * NUM_ROWS) + i;
                    dataBlob[`${ii}`] = res.data.data[i];
                }
                this.setState({
                    hasMore: true
                })
                console.log(dataBlob)
            
            } else {
                this.setState({
                    hasMore: false,
                    isLoading: false
                })
            }

        }).catch((error) => {
            console.log(error)
        })
        return dataBlob
       
        // return ['11', '22','33','44','55']
    }

    onEndReached = (event) => {
        console.log(this.state.isLoading, this.state.hasMore)
        console.log('到底部啦')
        // load new data
        // hasMore: from backend data, indicates whether it is the last page, here is false
        if ((!this.state.isLoading) && (!this.state.hasMore)) {
            console.log('没有数据啦')
            return;
        }
        console.log('reach end', event);
        this.setState({ isLoading: true });
        setTimeout(() => {
            this.rData = { ...this.rData, ...this.genData(++this.state.pageIndex) };
            this.setState({
                pageIndex: this.state.pageIndex + 1,
                dataSource: this.state.dataSource.cloneWithRows(this.rData),
                isLoading: false
            })
        }, 1000);
    }

    render() {
        const row = (rowData, sectionID, rowID) => {
            // if (index < 0) {
            //     index = this.state.data.length - 1;
            // }
            // const obj = this.state.data[index--];
            return (
                <Flex className="cooperative-store-list" onClick={() => { this.context.router.history.push(`/store/detail/${rowData.store.id}`) }}>
                    <Flex className="picture">
                        <img src="" alt="" />
                    </Flex>
                    <Flex direction="column" align="start" justify="between" style={{ height: '1.6rem', width: '5.24rem' }}>
                        <div className="store-name">
                            {rowData.store.name}   <span style={{ fontSize: '.18rem', color: '#e42f2f', marginLeft: '.14rem' }}>{rowData.star}分</span>
                        </div>
                        <Flex className="address" style={{ width: "100%" }}>
                            <div className="address-icon"></div>
                            <p className="info-font" style={{ width: '4.5rem' }}>{rowData.store.address}</p>
                        </Flex>
                        <Flex className="time" align="end" style={{ width: "100%" }}>
                            <div>
                                <Flex className="info-font">
                                    <div className="time-icon"></div>
                                    营业时间：{rowData.store.openingTime}-{rowData.store.closingTime}
                                </Flex>
                                {rowData.store.id == 1 && <div className="info-identify">
                                    <span className="identification">免费安全监测</span>
                                    <span className="identification">下雨保</span>
                                </div>}
                            </div>
                        </Flex>
                    </Flex>
                </Flex>
            );
        };


        return <div className="body-bac">
            <NavBar title="合作门店" />
            <ListView
                dataSource={this.state.dataSource}
                renderBodyComponent={() => <MyBody />}
                onEndReached={() => this.onEndReached()}
                onEndReachedThreshold={10}
                renderRow={row}
                initialListSize={10}
                pageSize={4}
                onScroll={() => { console.log('scroll'); }}
                scrollRenderAheadDistance={500}
                scrollEventThrottle={200}
                renderFooter={() => (<div style={{ padding: '.2rem', textAlign: 'center' }}>
                    {this.state.isLoading ? 'Loading...' : 'Loaded'}
                </div>)}
                style={{
                    height: `${document.documentElement.clientHeight}`,
                    overflow: 'auto',
                }}
            >

            </ListView>
        </div>
    }

}


const MyBody = (props) => {
    return (
        <div >{props.children}</div>
    );
}
export default CooperativeStore
CooperativeStore.contextTypes = {
    router: PropTypes.object.isRequired
}