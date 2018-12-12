/**
 * Created by yangyang on 2016/12/14.
 */
	$('#nav>ul>li').click(function(){
        $(this).children().addClass('active');
        $(this).siblings().children().removeClass('active');
    })

$(function(){
    var nav=$("#top"); 
    var win=$(window); 
    var sc=$(document);
    win.scroll(function(){
        if(sc.scrollTop()>=60){
            nav.addClass("fixed_nav");
        }
        else{
            nav.removeClass("fixed_nav");
        }
    })
})

$(function () {
    $(".bellows_content-wrapper").hide();
    $(".bellows_header").click(function (){
        $(this).next().slideDown()
        $(this).parent(".bellows_item").addClass("bellows_open")
        $(".bellows_header").not(this).next().slideUp()
        $(".bellows_header").not(this).parent(".bellows_item").removeClass("bellows_open")
    });
    $('.bellows_header').on('click', 'h3', function (e) {
        $(e.target).removeClass('active').siblings('.active').addClass('active');
    });
})


//搜索下拉
$('.seek').focus(function(){
        if($(this).hasClass('clickhover')){
            $(this).removeClass('clickhover');
            $(this).find('.seek_content').hide();
            $(this).find('img').attr('src','http://139.129.241.42/images/portal//header/header_normal.png');
        }else{
            $(this).addClass('clickhover');
            $(this).find('.seek_content').show();
            $(this).find('img').attr('src','http://139.129.241.42/images/portal//header/header_true.png');
        }
})
$('.seek_content>div').click(function(){
    $('.seek').removeClass('clickhover');
    var text=$(this).html();
    $('.seek span').html(text);
    $(this).parent().hide();
    $('.seek').find('img').attr('src','http://139.129.241.42/images/portal//header/header_normal.png');
    $('.seek').blur();

})

$('.seek').blur(function(){

        $('.seek').removeClass('clickhover');
        $('.seek_content').hide();
        $('.seek').find('img').attr('src','http://139.129.241.42/images/portal//header/header_normal.png');
})
$(function(){
    $('#banner .indicator li').click(function(){
        $(this).addClass('active').siblings('.active').removeClass('active');
        var i = $('#banner .indicator li').index(this);
        $($('#banner .inner .item')[i]).addClass('active').siblings('.active').removeClass('active');
    });
   
    var index =0;
    
    var timer = setInterval(function(){
        index = (index == 3) ? 0 : index + 1;
        
        $("#banner .indicator li").eq(index).addClass('active').siblings('.active').removeClass('active');
        $(".item").hide().eq(index).show();
    }, 3000);
})


$("#gourmet .food_detail .food1 a").addClass("gradient_pink");
$("#product_parameters_main .view_details ").addClass("gradient_blue");

$(".color_2").addClass("gradient_blue");
$(".color_1").addClass("gradient_pink");

$('.aside_nav>p').click(function(){
    if($(this).hasClass('')){
        $(this).addClass('click has');
        $(this).siblings().removeClass('click');
        var pic=$(this).find('img').attr('src');
      
        var slicepic=pic.slice(0,-6);
        var newpic=slicepic+'.png';
        $(this).find('img').attr('src',newpic);
        $(this).siblings().each(function(){
            if($(this).hasClass('has')){
                var pic1=$(this).find('img').attr('src');

                var slicepic1=pic1.slice(0,-4);
                var newpic1=slicepic1+'_1.png';
                $(this).find('img').attr('src',newpic1);
                $(this).removeClass('has');
            }
        })
    }


})




















