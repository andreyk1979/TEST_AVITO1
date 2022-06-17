package com.amr.project.util;

import com.amr.project.model.entity.*;
import com.amr.project.model.entity.report.SalesHistory;
import com.amr.project.model.enums.Gender;
import com.amr.project.model.enums.PersonalDataStatus;
import com.amr.project.model.enums.Roles;
import com.amr.project.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

@Component
public class Init {
    private final EntityManagerFactory entityManagerFactory;

    @Autowired
    public Init(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @PostConstruct
    @Transactional
    public void toDoTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        createUser(entityManager);
        createCountry(entityManager);
        createCity(entityManager);
        createAddress(entityManager);
        createPersonalData(entityManager);
        updateUsers(entityManager);
        createShop(entityManager);
        createUserInfo(entityManager);
        createCategories(entityManager);
        createItems(entityManager);
        createCartItem(entityManager);
        createFeedback(entityManager);
        createFavorites(entityManager);
        createOrders(entityManager);
        createCouponAndDiscount(entityManager);
        createSalesHistory(entityManager);
        entityManager.close();
    }

    private  void createSalesHistory(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        SalesHistory salesHistory = SalesHistory.builder()
                .price(BigDecimal.valueOf(25))
                .basePrice(BigDecimal.valueOf(5))
                .item(entityManager.find(Item.class, 4L))
                .count(1)
                .orderDate(Calendar.getInstance())
                .build();
        entityManager.persist(salesHistory);
        entityManager.getTransaction().commit();
        entityManager.getTransaction().begin();
        SalesHistory salesHistory2 = SalesHistory.builder()
                .price(BigDecimal.valueOf(250))
                .basePrice(BigDecimal.valueOf(9))
                .item(entityManager.find(Item.class, 7L))
                .count(5)
                .orderDate(Calendar.getInstance())
                .build();
        entityManager.persist(salesHistory2);
        entityManager.getTransaction().commit();
    }

    private void createUser(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        User user = new User();
        User user2 = new User();
        user2.setUsername("user");
        user2.setPassword("dogs");
        user.setUsername("seller");
        user.setPassword("cats");
        user.setActivate(true);
        user2.setActivate(true);
        user.setImage(entityManager.find(Image.class, 29L));
        user2.setImage(entityManager.find(Image.class, 30L));
        entityManager.persist(user);
        entityManager.persist(user2);
        entityManager.getTransaction().commit();
    }

    private void createCountry(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Country country = new Country();
        country.setName("Россия");
        entityManager.persist(country);
        entityManager.getTransaction().commit();
    }

    private void createPersonalData(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        PersonalData personalData = new PersonalData();
        personalData.setPlaceOfBirth("Санкт-Петербург");
        personalData.setAuthority("USER");
        personalData.setPassport(127498L);
        personalData.setComment("Придирчивый покупатель");
        Date dateOfPersonalData = new Date();
        dateOfPersonalData.setTime(12L);
        personalData.setDateOfIssue(dateOfPersonalData);
        personalData.setStatus(PersonalDataStatus.CONFIRMED);
        List<Image> listOfPersonalDataImages = new ArrayList<>();
        listOfPersonalDataImages.add(entityManager.find(Image.class, 31L));
        personalData.setListOfImages(listOfPersonalDataImages);
        entityManager.persist(personalData);
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        PersonalData personalData2 = new PersonalData();
        personalData2.setPlaceOfBirth("Москва");
        personalData2.setAuthority("ADMIN");
        personalData2.setPassport(604365L);
        personalData2.setComment("Добросовестный продавец");
        Date dateOfPersonalData2 = new Date();
        dateOfPersonalData2.setTime(15L);
        personalData2.setDateOfIssue(dateOfPersonalData2);
        personalData2.setStatus(PersonalDataStatus.CONFIRMED);
        List<Image> listOfPersonalDataImages2 = new ArrayList<>();
        listOfPersonalDataImages2.add(entityManager.find(Image.class, 32L));
        personalData2.setListOfImages(listOfPersonalDataImages2);
        entityManager.persist(personalData2);
        entityManager.getTransaction().commit();
    }

    private void createCity(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        City city = new City();
        city.setName("Санкт-Петерубрг");
        city.setCountry(entityManager.find(Country.class, 1L));
        entityManager.persist(city);
        City city2 = new City();
        city2.setName("Москва");
        city2.setCountry(entityManager.find(Country.class, 1L));
        entityManager.persist(city2);
        entityManager.getTransaction().commit();
    }

    private void createAddress(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Address address = new Address();
        address.setHouse("54а");
        address.setCityIndex(String.valueOf(101000));
        address.setStreet("Никольская ул.");
        address.setCity(entityManager.find(City.class, 2L));
        entityManager.persist(address);
        Address address2 = new Address();
        address2.setHouse("11/3");
        address2.setCityIndex(String.valueOf(187015));
        address2.setStreet("Миллионная ул.");
        address2.setCity(entityManager.find(City.class, 1L));
        entityManager.persist(address2);
        Address shopAddress = new Address();
        shopAddress.setHouse("Большой склад магазина");
        shopAddress.setCityIndex(String.valueOf(142050));
        shopAddress.setStreet("ул. Белые столбы");
        shopAddress.setCity(entityManager.find(City.class, 2L));
        entityManager.persist(shopAddress);
        entityManager.getTransaction().commit();
    }

    private void updateUsers(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Address addressForUserFromDB = entityManager.find(Address.class, 1L);
        User userFromDB = entityManager.find(User.class, 1L);
        userFromDB.setAddress(addressForUserFromDB);
        userFromDB.setRole(Roles.ADMIN);
        userFromDB.setEmail("seller@gmail.com");
        userFromDB.setPersonalData(entityManager.find(PersonalData.class, 1L));
        entityManager.merge(userFromDB);
        Address addressForUserFromDB2 = entityManager.find(Address.class, 2L);
        User userFromDB2 = entityManager.find(User.class, 2L);
        userFromDB2.setAddress(addressForUserFromDB2);
        userFromDB2.setRole(Roles.USER);
        userFromDB2.setEmail("user@gmail.com");
        userFromDB2.setPersonalData(entityManager.find(PersonalData.class, 2L));
        entityManager.merge(userFromDB2);
        entityManager.getTransaction().commit();
    }

    private void createShop(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Shop shop = new Shop();
        shop.setAddress(entityManager.find(Address.class, 3L));
        shop.setName("Samsung");
        shop.setEmail("samsung@gmail.com");
        shop.setPhone("88002528282");
        shop.setDescription("Официальный магазин Samsung");
        shop.setRating(8.99D);
        shop.setUser(entityManager.find(User.class, 2L));
        shop.setLogo(entityManager.find(Image.class, 1L));
        Image image = entityManager.find(Image.class, 1L);
        image.setShop(shop);
        entityManager.persist(shop);
        entityManager.getTransaction().commit();
    }

    private void createUserInfo(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        UserInfo userInfo = new UserInfo();
        userInfo.setUser(entityManager.find(User.class, 1L));
        userInfo.setPhone("88004762828");
        userInfo.setAge(26);
        userInfo.setGender(Gender.MALE);
        userInfo.setFirstName("Oleg");
        userInfo.setLastName("Sidorov");
        userInfo.setBirthday(Calendar.getInstance());
        entityManager.persist(userInfo);
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setUser(entityManager.find(User.class, 2L));
        userInfo2.setPhone("86004773828");
        userInfo2.setAge(40);
        userInfo2.setGender(Gender.FEMALE);
        userInfo2.setFirstName("Anna");
        userInfo2.setLastName("Ivanova");
        userInfo2.setBirthday(Calendar.getInstance());
        entityManager.persist(userInfo2);
        entityManager.getTransaction().commit();
    }

    private void createItems(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        List<Image> imagesOfItem1 = new ArrayList<>();
        for (long i = 20L; i < 23L; i++) {
            imagesOfItem1.add(entityManager.find(Image.class, i));
        }
        List<Image> imagesOfItem2 = new ArrayList<>();
        for (long i = 23L; i < 26L; i++) {
            imagesOfItem2.add(entityManager.find(Image.class, i));
        }
        List<Image> imagesOfItem3 = new ArrayList<>();
        for (long i = 26L; i < 29L; i++) {
            imagesOfItem3.add(entityManager.find(Image.class, i));
        }
        Item tv1 = new Item();
        tv1.setShop(entityManager.find(Shop.class, 1L));
        tv1.setImages(imagesOfItem1);
        tv1.setCount(10);
        tv1.setName("32\" Телевизор Samsung UE32T4500AU LED, черный");
        tv1.setDescription("Технология PurColor");
        tv1.setBasePrice(BigDecimal.valueOf(24_990));
        tv1.setRating(4.7);
        tv1.setPrice(BigDecimal.valueOf(24_990));
        tv1.setUser(entityManager.find(User.class, 2L));
        tv1.setCategory(entityManager.find(Category.class, 1L));
        Review reviewOfItem1 = new Review();
        reviewOfItem1.setItem(tv1);
        reviewOfItem1.setShop(entityManager.find(Shop.class, 1L));
        reviewOfItem1.setRating(9);
        reviewOfItem1.setDignity("1. Хорошая цена" +
                "2. Быстро доставили" +
                "3. Легко настроить бабушке");
        reviewOfItem1.setText("Купили телевизор в Вашем магазине. Остались довольны и магазином и телевизором. Спасибо!");
        reviewOfItem1.setUser(entityManager.find(User.class, 2L));
        reviewOfItem1.setDate(new Date());
        reviewOfItem1.setFlaw("Можно было бы и скидочку дать :)");
        reviewOfItem1.setModerateAccept(true);
        List<Review> reviewsOfItem1 = new ArrayList<>();
        reviewsOfItem1.add(reviewOfItem1);
        tv1.setReviews(reviewsOfItem1);
        entityManager.persist(tv1);

        Item tv2 = new Item();
        tv2.setShop(entityManager.find(Shop.class, 1L));
        tv2.setImages(imagesOfItem2);
        tv2.setCount(15);
        tv2.setCategory(entityManager.find(Category.class, 1L));
        tv2.setName("65\" Телевизор Samsung UE65AU7002U, черный");
        tv2.setDescription("Технология PurColor");
        tv2.setRating(8.55);
        tv2.setBasePrice(BigDecimal.valueOf(98_305));
        tv2.setUser(entityManager.find(User.class, 2L));
        tv2.setPrice(BigDecimal.valueOf(98_305));
        Review reviewOfItem2 = new Review();
        reviewOfItem2.setItem(tv2);
        reviewOfItem2.setShop(entityManager.find(Shop.class, 1L));
        reviewOfItem2.setRating(9);
        reviewOfItem2.setDignity("1. Хорошая яркость" +
                "2. Отлинчый звук");
        reviewOfItem2.setText("Привезли быстро, спасибо!");
        reviewOfItem2.setUser(entityManager.find(User.class, 2L));
        reviewOfItem2.setModerateAccept(true);
        reviewOfItem2.setDate(new Date());
        reviewOfItem2.setFlaw("Да вроде все норм");
        List<Review> listReviewsOfItem2 = new ArrayList<>();
        listReviewsOfItem2.add(reviewOfItem2);
        tv2.setReviews(listReviewsOfItem2);
        entityManager.persist(tv2);

        Item tv3 = new Item();
        tv3.setShop(entityManager.find(Shop.class, 1L));
        tv3.setImages(imagesOfItem3);
        tv3.setName("40\" Телевизор Samsung UE40T5300 2020 HDR, LED, черный");
        tv3.setDescription("Технология PurColor");
        tv3.setCategory(entityManager.find(Category.class, 1L));
        tv3.setCount(22);
        tv3.setRating(8.22);
        tv3.setBasePrice(BigDecimal.valueOf(56_995));
        tv3.setUser(entityManager.find(User.class, 2L));
        tv3.setPrice(BigDecimal.valueOf(56_995));
        Review reviewOfItem3 = new Review();
        reviewOfItem3.setItem(tv3);
        reviewOfItem3.setShop(entityManager.find(Shop.class, 1L));
        reviewOfItem3.setRating(9);
        reviewOfItem3.setDignity("Не нашел");
        reviewOfItem3.setText("Норм за свои деньги");
        reviewOfItem3.setModerateAccept(true);
        reviewOfItem3.setDate(new Date());
        reviewOfItem3.setFlaw("Не такой красивый, как на картинке");
        reviewOfItem3.setUser(entityManager.find(User.class, 2L));
        List<Review> listReviewsOfItem3 = new ArrayList<>();
        listReviewsOfItem3.add(reviewOfItem3);
        tv3.setReviews(listReviewsOfItem3);
        entityManager.persist(tv3);

        List<Image> listOfImagesPhoneItem1 = new ArrayList<>();
        for (long i = 11; i < 14; i++) {
            listOfImagesPhoneItem1.add(entityManager.find(Image.class, i));
        }
        List<Image> listOfImagesPhoneItem2 = new ArrayList<>();
        for (long i = 14; i < 17; i++) {
            listOfImagesPhoneItem2.add(entityManager.find(Image.class, i));
        }
        List<Image> listOfImagesPhoneItem3 = new ArrayList<>();
        for (long i = 17; i < 20; i++) {
            listOfImagesPhoneItem3.add(entityManager.find(Image.class, i));
        }
        Item itemPhone1 = new Item();
        itemPhone1.setCount(25);
        itemPhone1.setCategory(entityManager.find(Category.class, 2L));
        itemPhone1.setImages(listOfImagesPhoneItem1);
        itemPhone1.setName("Galaxy S22 Ultra");
        itemPhone1.setDescription("Встречайте Galaxy S22 Ultra с мощью Note. Его тонкий алюминиевый корпус впечатляет своей совершенной формой!");
        itemPhone1.setRating(9.3);
        itemPhone1.setShop(entityManager.find(Shop.class, 1L));
        itemPhone1.setPrice(BigDecimal.valueOf(99_999));
        itemPhone1.setBasePrice(BigDecimal.valueOf(97_995));
        itemPhone1.setUser(entityManager.find(User.class, 2L));
        itemPhone1.setShop(entityManager.find(Shop.class, 1l));
        Review itemPhone1Review = new Review();
        itemPhone1Review.setItem(itemPhone1);
        itemPhone1Review.setText("Радует больше чем s21");
        itemPhone1Review.setDignity("Работает все как всегда отлично, за месяц порядка 5 обновлений системы уже прилетело. Собран качественно.");
        itemPhone1Review.setUser(entityManager.find(User.class, 2L));
        itemPhone1Review.setRating(9);
        itemPhone1Review.setShop(entityManager.find(Shop.class, 1L));
        itemPhone1Review.setDate(new Date());
        itemPhone1Review.setFlaw("Не замечено");
        itemPhone1Review.setModerateAccept(true);
        List<Review> reviewOfItemPhone1 = new ArrayList<>();
        reviewOfItemPhone1.add(itemPhone1Review);
        itemPhone1.setReviews(reviewOfItemPhone1);
        entityManager.persist(itemPhone1);

        Item itemPhone2 = new Item();
        itemPhone2.setUser(entityManager.find(User.class, 2L));
        itemPhone2.setImages(listOfImagesPhoneItem2);
        itemPhone2.setShop(entityManager.find(Shop.class, 1L));
        itemPhone2.setCategory(entityManager.find(Category.class, 2L));
        itemPhone2.setDescription("С видео 8K и функцией 8K Video Snap, Galaxy S20, S20+ и S20 Ultra изменят ваш взгляд на фото- и видеосъемку.");
        itemPhone2.setName("Galaxy S20 FE");
        itemPhone2.setCount(15);
        itemPhone2.setPrice(BigDecimal.valueOf(86_999));
        itemPhone2.setBasePrice(BigDecimal.valueOf(84_995));
        itemPhone2.setRating(9.66);
        Review itemPhone2Review = new Review();
        itemPhone2Review.setItem(itemPhone2);
        itemPhone2Review.setText("всё время пользовался серия А теперь только S");
        itemPhone2Review.setDignity("прошёл год а он всё как новый летает.");
        itemPhone2Review.setUser(entityManager.find(User.class, 2L));
        itemPhone2Review.setRating(9);
        itemPhone2Review.setShop(entityManager.find(Shop.class, 1L));
        itemPhone2Review.setDate(new Date());
        itemPhone2Review.setFlaw("Нет");
        itemPhone2Review.setModerateAccept(true);
        List<Review> reviewOfItemPhone2 = new ArrayList<>();
        reviewOfItemPhone2.add(itemPhone2Review);
        itemPhone2.setReviews(reviewOfItemPhone2);
        entityManager.persist(itemPhone2);

        Item itemPhone3 = new Item();
        itemPhone3.setUser(entityManager.find(User.class, 2L));
        itemPhone3.setImages(listOfImagesPhoneItem3);
        itemPhone3.setShop(entityManager.find(Shop.class,1L));
        itemPhone3.setCategory(entityManager.find(Category.class, 2L));
        itemPhone3.setDescription("Samsung не перестаёт покорять сердца пользователей и выпускает новый смартфон - Samsung Galaxy A52");
        itemPhone3.setName("Смартфон Samsung Galaxy A52 8/256 ГБ RU, лаванда");
        itemPhone3.setCount(25);
        itemPhone3.setPrice(BigDecimal.valueOf(46_083));
        itemPhone3.setBasePrice(BigDecimal.valueOf(43_995));
        itemPhone3.setRating(9.56);
        Review itemPhone3Review = new Review();
        itemPhone3Review.setItem(itemPhone3);
        itemPhone3Review.setText("Выбирал между этим аппаратом и Apple. Изучил тестовые видео - остался доволен. Когда взял, убедился в том,, что для моих целей его достаточно.");
        itemPhone3Review.setDignity("Стильный телефон, удобен в использовании, батареи хватает на сутки при активном использовании (музыка, кино, разговор)");
        itemPhone3Review.setUser(entityManager.find(User.class, 2L));
        itemPhone3Review.setRating(9);
        itemPhone3Review.setShop(entityManager.find(Shop.class, 1L));
        itemPhone3Review.setDate(new Date());
        itemPhone3Review.setFlaw("Отсутствие беспроводной зарядки это чистый маркетинг для продвижения флагманов. Копеечную функцию зажали))");
        itemPhone3Review.setModerateAccept(true);
        List<Review> reviewOfItemPhone3 = new ArrayList<>();
        reviewOfItemPhone3.add(itemPhone3Review);
        itemPhone3.setReviews(reviewOfItemPhone3);
        entityManager.persist(itemPhone3);

        List<Image> listOfImagesHeadPhoneItem1 = new ArrayList<>();
        for (long i = 2; i < 5; i++) {
            listOfImagesHeadPhoneItem1.add(entityManager.find(Image.class, i));
        }
        List<Image> listOfImagesHeadPhoneItem2 = new ArrayList<>();
        for (long i = 5; i < 8; i++) {
            listOfImagesHeadPhoneItem2.add(entityManager.find(Image.class, i));
        }
        List<Image> listOfImagesHeadPhoneItem3 = new ArrayList<>();
        for (long i = 8; i < 11; i++) {
            listOfImagesHeadPhoneItem3.add(entityManager.find(Image.class, i));
        }
        Item headPhone = new Item();
        headPhone.setUser(entityManager.find(User.class, 2L));
        headPhone.setImages(listOfImagesHeadPhoneItem1);
        headPhone.setShop(entityManager.find(Shop.class,1L));
        headPhone.setCategory(entityManager.find(Category.class, 3L));
        headPhone.setDescription("Элегантные наушники Galaxy Buds Live приковывают к себе взгляды, даже когда находятся у вас в ушах.");
        headPhone.setName("Беспроводные наушники Samsung Galaxy Buds Live, черный");
        headPhone.setCount(15);
        headPhone.setPrice(BigDecimal.valueOf(15_083));
        headPhone.setBasePrice(BigDecimal.valueOf(13_905));
        headPhone.setRating(9.03);
        Review headPhoneReview = new Review();
        headPhoneReview.setItem(itemPhone3);
        headPhoneReview.setText("Красивые, удобные. Хороший звук. Дизайн.");
        headPhoneReview.setDignity("Технология активного шумоподавления");
        headPhoneReview.setUser(entityManager.find(User.class, 2L));
        headPhoneReview.setRating(9);
        headPhoneReview.setShop(entityManager.find(Shop.class, 1L));
        headPhoneReview.setDate(new Date());
        headPhoneReview.setFlaw("Коробочка легко царапается, краска слезает.");
        headPhoneReview.setModerateAccept(true);
        List<Review> reviewOfHeadPhone = new ArrayList<>();
        reviewOfHeadPhone.add(headPhoneReview);
        headPhone.setReviews(reviewOfHeadPhone);
        entityManager.persist(headPhone);

        Item headPhone2 = new Item();
        headPhone2.setUser(entityManager.find(User.class, 2L));
        headPhone2.setImages(listOfImagesHeadPhoneItem2);
        headPhone2.setShop(entityManager.find(Shop.class,1L));
        headPhone2.setCategory(entityManager.find(Category.class, 3L));
        headPhone2.setDescription("Это беспроводные наушники с технологиями профессионального уровня, обеспечивающими невероятное погружение в захватывающий звук.");
        headPhone2.setName("Беспроводные наушники Samsung Galaxy Buds Pro, фиолетовый");
        headPhone2.setCount(25);
        headPhone2.setPrice(BigDecimal.valueOf(13_683));
        headPhone2.setBasePrice(BigDecimal.valueOf(11_885));
        headPhone2.setRating(9.43);
        Review headPhoneReview2 = new Review();
        headPhoneReview2.setItem(headPhone2);
        headPhoneReview2.setText("Работает действительно долго, разрядить до нуля в течение дня пока не получилось.");
        headPhoneReview2.setDignity("Технология активного шумоподавления");
        headPhoneReview2.setUser(entityManager.find(User.class, 2L));
        headPhoneReview2.setRating(8);
        headPhoneReview2.setShop(entityManager.find(Shop.class, 1L));
        headPhoneReview2.setDate(new Date());
        headPhoneReview2.setFlaw("Нет возможности подключить одновременно к двум разным устройствам.");
        headPhoneReview2.setModerateAccept(true);
        List<Review> reviewOfHeadPhone2 = new ArrayList<>();
        reviewOfHeadPhone2.add(headPhoneReview2);
        headPhone2.setReviews(reviewOfHeadPhone2);
        entityManager.persist(headPhone2);

        Item headPhone3 = new Item();
        headPhone3.setUser(entityManager.find(User.class, 2L));
        headPhone3.setImages(listOfImagesHeadPhoneItem3);
        headPhone3.setShop(entityManager.find(Shop.class,1L));
        headPhone3.setCategory(entityManager.find(Category.class, 3L));
        headPhone3.setDescription("Чистый и реалистичный звук от самых высоких нот до самых глубоких басов благодаря специально разработанным двухполосным динамикам со звуком от специалистов AKG.");
        headPhone3.setName("Беспроводные наушники Samsung Galaxy Buds Pro, белый");
        headPhone3.setCount(30);
        headPhone3.setPrice(BigDecimal.valueOf(19_622));
        headPhone3.setBasePrice(BigDecimal.valueOf(17_845));
        headPhone3.setRating(9.11);
        Review headPhoneReview3 = new Review();
        headPhoneReview3.setItem(headPhone2);
        headPhoneReview3.setText("Звук великолепный не только с устройствами samsung.");
        headPhoneReview3.setDignity("Технология активного шумоподавления");
        headPhoneReview3.setUser(entityManager.find(User.class, 2L));
        headPhoneReview3.setRating(8);
        headPhoneReview3.setShop(entityManager.find(Shop.class, 1L));
        headPhoneReview3.setDate(new Date());
        headPhoneReview3.setFlaw("Странная форма наушников, возможно дело привычки.");
        headPhoneReview3.setModerateAccept(true);
        List<Review> reviewOfHeadPhone3 = new ArrayList<>();
        reviewOfHeadPhone3.add(headPhoneReview3);
        headPhone3.setReviews(reviewOfHeadPhone3);
        entityManager.persist(headPhone3);
        entityManager.getTransaction().commit();
    }
    private void createCategories(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Category tvCategory = new Category();
        tvCategory.setName("Телевизоры");
        entityManager.persist(tvCategory);
        Category phonesCategory = new Category();
        phonesCategory.setName("Телефоны");
        entityManager.persist(phonesCategory);
        Category headPhonesCategory = new Category();
        headPhonesCategory.setName("Наушники");
        entityManager.persist(headPhonesCategory);
        entityManager.getTransaction().commit();
    }

    private void createCartItem(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(5);
        cartItem.setShop(entityManager.find(Shop.class, 1L));
        cartItem.setUser(entityManager.find(User.class, 2L));
        entityManager.persist(cartItem);
        entityManager.getTransaction().commit();
    }

    private void createFeedback(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Feedback feedback = new Feedback();
        feedback.setShop(entityManager.find(Shop.class, 1L));
        feedback.setUsername(entityManager.find(User.class, 2L).getUsername());
        feedback.setUser(entityManager.find(User.class, 2L));
        feedback.setFullText("Хороший магазин и выбор товаров");
        feedback.setReason("Тестовый фидбэк");
        feedback.setDateTime(LocalDateTime.now());
        entityManager.persist(feedback);
        entityManager.getTransaction().commit();
    }

    private void createFavorites(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Favorite favorite = new Favorite();
        List<Item> favorites = new ArrayList<>();
        favorites.add(entityManager.find(Item.class, 1L));
        favorites.add(entityManager.find(Item.class, 5L));
        favorites.add(entityManager.find(Item.class, 8L));
        favorite.setItems(favorites);
        favorite.setUser(entityManager.find(User.class, 2L));
        List<Shop> favShops = new ArrayList<>();
        favShops.add(entityManager.find(Shop.class, 1L));
        favorite.setShops(favShops);
        entityManager.persist(favorite);
        entityManager.getTransaction().commit();
    }

    private void createOrders(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Order order = new Order();
        order.setAddress(entityManager.find(Address.class, 1L));
        order.setUser(entityManager.find(User.class, 2L));
        order.setStatus(Status.SENT);
        order.setOrderDate(Calendar.getInstance());
        order.setExpectedDeliveryDate(Calendar.getInstance());
        Item item1 = entityManager.find(Item.class, 4L);
        Item item2 = entityManager.find(Item.class, 7L);
        order.setGrandTotal(item2.getPrice().add(item1.getPrice()));
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        order.setItemsInOrder(items);
        order.setCurrency("RUB");
        order.setDescription("Fragile!");
        entityManager.persist(order);
        User user = entityManager.find(User.class, 2L);
        List<Order> orders = new ArrayList<>();
        orders.add(entityManager.find(Order.class, 1L));
        user.setOrders(orders);
        entityManager.merge(user);
        entityManager.getTransaction().commit();
    }

    private void createCouponAndDiscount(EntityManager entityManager) {
        entityManager.getTransaction().begin();
        Coupon coupon = new Coupon();
        coupon.setUser(entityManager.find(User.class, 2L));
        coupon.setEnd(Calendar.getInstance());
        coupon.setStart(Calendar.getInstance());
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(coupon);
        Shop shop = entityManager.find(Shop.class, 1L);
        shop.setCoupons(coupons);
        entityManager.persist(coupon);
        Discount discount = new Discount();
        discount.setShop(entityManager.find(Shop.class, 1L));
        discount.setMinOrder(2);
        discount.setFixedDiscount(200);
        discount.setPercentage(3);
        entityManager.persist(discount);
        entityManager.getTransaction().commit();
    }
}