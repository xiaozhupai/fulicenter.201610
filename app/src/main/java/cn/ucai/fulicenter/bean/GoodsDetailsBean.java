package cn.ucai.fulicenter.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/1/9.
 */

public class GoodsDetailsBean {
    /**
     * id : 278
     * goodsId : 7672
     * catId : 291
     * goodsName : 趣味煮蛋模具
     * goodsEnglishName : Kotobuki
     * goodsBrief : 将煮好的鸡蛋放到模具中，扣好卡扣，把蛋模放冰水，耐心等上10分钟，就可以变化成各种各样的形状，宝宝看了说不定胃口大开！
     * shopPrice : ￥110
     * currencyPrice : ￥140
     * promotePrice : ￥0
     * rankPrice : ￥140
     * isPromote : false
     * goodsThumb : 201509/thumb_img/7672_thumb_G_1442389445719.jpg
     * goodsImg : 201509/thumb_img/7672_thumb_G_1442389445719.jpg
     * addTime : 1476820611547
     * shareUrl : http://m.fulishe.com/item/7672
     * properties : [{"id":9522,"goodsId":0,"colorId":4,"colorName":"绿色","colorCode":"#59d85c","colorImg":"201309/1380064997570506166.jpg","colorUrl":"1","albums":[{"pid":7672,"imgId":28283,"imgUrl":"201509/goods_img/7672_P_1442389445199.jpg","thumbUrl":"no_picture.gif"}]}]
     * promote : false
     */

    private int id;
    private int goodsId;
    private int catId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsBrief;
    private String shopPrice;
    private String currencyPrice;
    private String promotePrice;
    private String rankPrice;
    private boolean isPromote;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;
    private String shareUrl;
    private boolean promote;
    private List<PropertiesBean> properties;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public String getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(String promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getRankPrice() {
        return rankPrice;
    }

    public void setRankPrice(String rankPrice) {
        this.rankPrice = rankPrice;
    }

    public boolean isIsPromote() {
        return isPromote;
    }

    public void setIsPromote(boolean isPromote) {
        this.isPromote = isPromote;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public boolean isPromote() {
        return promote;
    }

    public void setPromote(boolean promote) {
        this.promote = promote;
    }

    public List<PropertiesBean> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertiesBean> properties) {
        this.properties = properties;
    }

    public static class PropertiesBean {
        /**
         * id : 9522
         * goodsId : 0
         * colorId : 4
         * colorName : 绿色
         * colorCode : #59d85c
         * colorImg : 201309/1380064997570506166.jpg
         * colorUrl : 1
         * albums : [{"pid":7672,"imgId":28283,"imgUrl":"201509/goods_img/7672_P_1442389445199.jpg","thumbUrl":"no_picture.gif"}]
         */

        private int id;
        private int goodsId;
        private int colorId;
        private String colorName;
        private String colorCode;
        private String colorImg;
        private String colorUrl;
        private List<AlbumsBean> albums;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getColorId() {
            return colorId;
        }

        public void setColorId(int colorId) {
            this.colorId = colorId;
        }

        public String getColorName() {
            return colorName;
        }

        public void setColorName(String colorName) {
            this.colorName = colorName;
        }

        public String getColorCode() {
            return colorCode;
        }

        public void setColorCode(String colorCode) {
            this.colorCode = colorCode;
        }

        public String getColorImg() {
            return colorImg;
        }

        public void setColorImg(String colorImg) {
            this.colorImg = colorImg;
        }

        public String getColorUrl() {
            return colorUrl;
        }

        public void setColorUrl(String colorUrl) {
            this.colorUrl = colorUrl;
        }

        public List<AlbumsBean> getAlbums() {
            return albums;
        }

        public void setAlbums(List<AlbumsBean> albums) {
            this.albums = albums;
        }

        public static class AlbumsBean {
            /**
             * pid : 7672
             * imgId : 28283
             * imgUrl : 201509/goods_img/7672_P_1442389445199.jpg
             * thumbUrl : no_picture.gif
             */

            private int pid;
            private int imgId;
            private String imgUrl;
            private String thumbUrl;

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getImgId() {
                return imgId;
            }

            public void setImgId(int imgId) {
                this.imgId = imgId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getThumbUrl() {
                return thumbUrl;
            }

            public void setThumbUrl(String thumbUrl) {
                this.thumbUrl = thumbUrl;
            }
        }
    }
}
