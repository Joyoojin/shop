package com.shop.entity;

import com.shop.constant.ItemSellStatus;
import com.shop.constant.PCategory;
import com.shop.dto.ItemFormDto;
import com.shop.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;       //상품 코드

    @Column(nullable = false, length = 50)
    private String itemNm; //상품명

    @Column(name = "price", nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세 설명

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; //상품 판매 상태


    //@Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PCategory pCategory;      // 상품 분류

    // private int pDiscount;      // 할인율

    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.stockNumber = itemFormDto.getStockNumber();
        this.itemDetail = itemFormDto.getItemDetail();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
        this.pCategory = itemFormDto.getPCategory();      //  카테고리 변경 추가
    }

    /**
     * 재고 감소
     */
    public void removeStock(int stockNumber) {
        int restStock = this.stockNumber - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족 합니다. (현재 재고 수량: " + this.stockNumber + ")");  // 재고가 부족할 경우 예외를 발생한다.
        }
        this.stockNumber = restStock;
    }

    /**
     * 재고 추가
     */
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }

}