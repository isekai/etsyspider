package com.company.processor;

import com.company.constant.EtsyConstant;
import com.company.entity.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author doctor
 * @date 2019/9/21
 **/
public class ProductProcessor implements PageProcessor {
    private Site site = Site.me().setDomain(EtsyConstant.DOMAIN)
            .setTimeOut(EtsyConstant.TIME_OUT)
            .setUserAgent(EtsyConstant.USER_AGENT);

    @Override
    public void process(Page page) {
        Element head = page.getHtml().getDocument().head();
        String json = head.select("script[type='application/ld+json']").html();
        ObjectMapper objectMapper = new ObjectMapper();
        Product product = new Product();
        try {
            Map<String, Object> map = objectMapper.readValue(json, LinkedHashMap.class);
            product.setUrl(objectToString(map.get("url")));
            product.setName(objectToString(map.get("name")));
            product.setImg(objectToString(map.get("image")));
            product.setDescription(objectToString(map.get("description")));
            product.setBrand(objectToString(map.get("brand")));
            product.setLogo(objectToString(map.get("logo")));
            Map<String, Object> offers = (Map<String, Object>) map.get("offers");
            product.setPriceCurrency(objectToString(offers.get("priceCurrency")));
            product.setLowPrice(objectToBigDecimal(offers.get("lowPrice")));
            product.setHighPrice(objectToBigDecimal(offers.get("highPrice")));
            Map<String, Object> aggregateRating = (Map<String, Object>) map.get("aggregateRating");
            product.setRatingValue(objectToBigDecimal(aggregateRating.get("ratingValue")));
            product.setRatingCount(objectToInt(aggregateRating.get("ratingCount")));
            product.setReviewCount(objectToInt(aggregateRating.get("reviewCount")));

            Element body = page.getHtml().getDocument().body();
            Element variations = body.getElementById("variations");
            Elements selects = variations.getElementsByTag("select");
            Map<String, List<String>> property = new LinkedHashMap<>();
            for (Element select : selects) {
                if ("inventory-variation-select-quantity".equals(select.id())) {
                    continue;
                }
                String label = variations.getElementsByAttributeValue("for", select.id()).text();
                Elements options = select.getElementsByTag("option");
                List<String> optionList = options.stream().map(Element::text).collect(Collectors.toList());
                optionList.remove(0);
                property.put(label, optionList);
            }
            if (!property.isEmpty()) {
                String propertyJson = objectMapper.writeValueAsString(property);
                product.setProperty(propertyJson);
            }

            Elements p = body.getElementById("listing-right-column")
                    .getElementsByClass("listing-page-overview-component").first()
                    .getElementsByTag("p");
            if (p.size() > 0) {
                product.setCraft(p.get(0).text());
            }
            if (p.size() > 1) {
                product.setRawMaterial(p.get(1).text());
            }

            Elements li = body.getElementsByAttribute("data-listing-tag-list").first()
                    .getElementsByTag("li");
            StringBuilder stringBuilder = new StringBuilder();
            for (Element element : li) {
                stringBuilder.append(element.text());
                stringBuilder.append(",");
            }
            product.setKeyword(stringBuilder.toString());

            Elements imgs = body
                    .select("ul[class='list-unstyled overflow-hidden position-relative carousel-pane-list']")
                    .first()
                    .getElementsByTag("img");
            List<String> imgList = imgs.stream()
                    .map(element -> element.attr("data-src-zoom-image"))
                    .collect(Collectors.toList());
            product.setImgList(objectMapper.writeValueAsString(imgList));
        } catch (IOException e) {
            e.printStackTrace();
        }
        page.putField(product.getDataId(), product);
    }

    @Override
    public Site getSite() {
        return site;
    }

    private String objectToString(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return null;
    }

    private BigDecimal objectToBigDecimal(Object obj) {
        if (obj != null) {
            String string = obj.toString().replaceAll(",", "");
            double d = Double.parseDouble(string);
            return BigDecimal.valueOf(d);
        }
        return null;
    }

    private int objectToInt(Object obj) {
        if (obj != null) {
            return Integer.parseInt(obj.toString());
        }
        return 0;
    }

}
