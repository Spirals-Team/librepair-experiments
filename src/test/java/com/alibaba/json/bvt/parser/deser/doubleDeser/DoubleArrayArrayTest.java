package com.alibaba.json.bvt.parser.deser.doubleDeser;

import com.alibaba.fastjson.JSON;
import junit.framework.TestCase;

/**
 * Created by wenshao on 07/01/2017.
 */
public class DoubleArrayArrayTest extends TestCase {
    public void test_double_array() throws Exception {
        String text = "{\"matrix\":[[0.172129,0.723352,0.177995,0.721544,0.182355,0.730706,0.176919,0.732701,0.186608,0.720192,0.190068,0.729511,0.192888,0.736099,0.188924,0.738652,0.184406,0.741017,0.170437,0.712842,0.176347,0.711257,0.170267,0.700188,0.176057,0.698837,0.184787,0.697703,0.185179,0.70996,0.208504,0.744638,0.207185,0.75156,0.197198,0.745556,0.199589,0.739859,0.205733,0.755607,0.194191,0.748785,0.219349,0.749149,0.218894,0.756673,0.231387,0.75334,0.23149,0.761106,0.231659,0.765365,0.218491,0.760977,0.253877,0.771869,0.253654,0.767861,0.256866,0.768794,0.257065,0.772761,0.253434,0.759933,0.256658,0.760855,0.256698,0.760878,0.256906,0.768818,0.257104,0.772784,0.256506,0.729776,0.256602,0.746778,0.256563,0.746758,0.256466,0.729762,0.253365,0.746039,0.253305,0.729353,0.208608,0.734533,0.219643,0.737812,0.208154,0.722732,0.219734,0.724631,0.231654,0.726482,0.231525,0.740979,0.198785,0.731277,0.196886,0.720919,0.252837,0.701907,0.255978,0.70208,0.25626,0.71584,0.253111,0.715594,0.256017,0.702087,0.256299,0.71585,0.207482,0.698671,0.21932,0.699664,0.219582,0.712605,0.207794,0.71135,0.231284,0.700637,0.231524,0.713835,0.195993,0.71023,0.195616,0.697833,0.184535,0.550769,0.184432,0.568474,0.175924,0.56888,0.176165,0.55098,0.184309,0.587294,0.175732,0.587834,0.170197,0.588354,0.170474,0.569338,0.170864,0.551281,0.20792,0.55119,0.207833,0.568574,0.195499,0.568448,0.19558,0.55092,0.207654,0.586916,0.19536,0.587032,0.23198,0.551393,0.232034,0.568596,0.220139,0.568628,0.220174,0.551333,0.231943,0.586705,0.21996,0.586819,0.25366,0.5514,0.253927,0.568279,0.253892,0.56828,0.253626,0.5514,0.254347,0.586341,0.254311,0.586341,0.251524,0.586391,0.251178,0.568326,0.250939,0.551401,0.171301,0.53406,0.176453,0.534027,0.171666,0.517244,0.176709,0.517407,0.184769,0.517577,0.184646,0.53404,0.187073,0.433013,0.186693,0.447362,0.178186,0.447021,0.178469,0.43254,0.186279,0.459648,0.177904,0.459465,0.172536,0.459376,0.17264,0.446764,0.172772,0.432129,0.254709,0.605955,0.251855,0.606001,0.254745,0.605955,0.255063,0.625956,0.255025,0.625956,0.252102,0.625997,0.219841,0.606546,0.20753,0.606811,0.231883,0.606327,0.231732,0.626313,0.21966,0.626578,0.207385,0.626913,0.195207,0.607143,0.195071,0.627324,0.184012,0.6278,0.184138,0.607566,0.175591,0.608112,0.170098,0.608578,0.175506,0.628336,0.170054,0.628771,0.184169,0.664205,0.175552,0.66494,0.175478,0.646989,0.184025,0.646379,0.169897,0.665737,0.169944,0.647562,0.207146,0.645853,0.194985,0.646046,0.207009,0.664252,0.195024,0.664082,0.231372,0.645484,0.219298,0.645671,0.231067,0.664478,0.219017,0.664403,0.25524,0.645062,0.255202,0.645062,0.255407,0.664284,0.255369,0.664283,0.252291,0.664316,0.252198,0.645116,0.175768,0.682855,0.184437,0.68193,0.170046,0.68392,0.207171,0.682448,0.195264,0.681939,0.231085,0.683568,0.219078,0.683034,0.255693,0.684166,0.255654,0.684162,0.252534,0.684097,0.178912,0.413718,0.187542,0.41424,0.173121,0.413242,0.173504,0.393704,0.17933,0.394184,0.187943,0.394696,0.209613,0.395196,0.209544,0.414815,0.198074,0.414577,0.198324,0.394991,0.209455,0.433637,0.19777,0.433375,0.232554,0.433896,0.22102,0.433801,0.220989,0.41503,0.232488,0.415241,0.220946,0.39543,0.232399,0.395719,0.255891,0.396519,0.255908,0.41571,0.255871,0.41571,0.255854,0.396518,0.255864,0.433988,0.255828,0.433988,0.25288,0.433978,0.252905,0.415647,0.252875,0.396409,0.187834,0.33264,0.188059,0.347108,0.179719,0.346794,0.17964,0.332433,0.188127,0.362009,0.179638,0.361564,0.173914,0.361136,0.17414,0.346458,0.174208,0.332159,0.209313,0.332309,0.209523,0.347121,0.198282,0.347186,0.198008,0.332558,0.209614,0.362335,0.198411,0.362241,0.255985,0.331191,0.255885,0.346601,0.25585,0.346602,0.255951,0.331191,0.255831,0.362419,0.255794,0.362419,0.252802,0.362408,0.252883,0.346632,0.253,0.331256,0.232266,0.362355,0.220862,0.362357,0.220919,0.346996,0.232449,0.34685,0.220897,0.332016,0.232599,0.331714,0.173913,0.317859,0.179257,0.318157,0.173526,0.304093,0.178809,0.304461,0.186888,0.304705,0.187376,0.318361,0.208558,0.304006,0.20893,0.31784,0.197555,0.318204,0.197111,0.304487,0.220637,0.317415,0.220306,0.303455,0.232212,0.302857,0.23248,0.316972,0.25309,0.31625,0.25607,0.316145,0.253092,0.301759,0.256116,0.301594,0.256151,0.301591,0.256104,0.316144,0.255812,0.378915,0.25282,0.378839,0.255849,0.378915,0.20964,0.378064,0.220887,0.378207,0.232287,0.378377,0.198431,0.377903,0.188113,0.377627,0.17954,0.377129,0.173735,0.376658,0.189535,0.24785,0.187786,0.262645,0.179913,0.261978,0.181918,0.247124,0.186763,0.278702,0.178782,0.278285,0.17362,0.277679,0.174751,0.261102,0.176759,0.246022,0.193439,0.231805,0.191903,0.237718,0.18479,0.237335,0.18917,0.230092,0.179687,0.236154,0.184437,0.228594,0.208886,0.245335,0.208742,0.261367,0.19771,0.262347,0.1987,0.247114,0.208432,0.277596,0.197009,0.278374,0.230933,0.240064,0.231468,0.25818,0.21994,0.259977,0.219568,0.242933,0.231645,0.275501,0.219942,0.276657,0.255944,0.233467,0.255854,0.25349,0.255819,0.253503,0.255907,0.233486,0.255891,0.272442,0.255856,0.272451,0.252775,0.272867,0.252708,0.254137,0.252693,0.234351,0.201437,0.213448,0.204125,0.216611,0.195697,0.223216,0.191909,0.220851,0.206985,0.222545,0.199212,0.227872,0.208436,0.232191,0.199707,0.235722,0.212358,0.206835,0.214206,0.210213,0.225151,0.200631,0.226311,0.20407,0.227901,0.211048,0.216462,0.216791,0.218328,0.227974,0.229593,0.223474,0.256448,0.200348,0.256404,0.200375,0.256583,0.192308,0.256631,0.192278,0.252658,0.201707,0.252603,0.193778,0.25255,0.189796,0.256687,0.188239,0.256737,0.188208,0.252682,0.215748,0.25614,0.214625,0.25618,0.2146,0.178532,0.291849,0.186574,0.29214,0.17332,0.291399,0.208381,0.291222,0.196869,0.291844,0.220069,0.290534,0.231913,0.289751,0.256,0.287837,0.256035,0.287832,0.252945,0.288095,0.176854,0.500755,0.184906,0.500874,0.171839,0.500678,0.171912,0.492955,0.176953,0.492959,0.185044,0.493022,0.177464,0.472003,0.172267,0.472047,0.185705,0.472043,0.185179,0.485359,0.177052,0.485356,0.171984,0.485426,0.208101,0.493423,0.207892,0.501199,0.195761,0.50103,0.195941,0.493208,0.207669,0.517746,0.195574,0.517664,0.209368,0.448041,0.197503,0.447722,0.209178,0.460308,0.197164,0.459967,0.207772,0.534335,0.195559,0.534164,0.20878,0.472569,0.196645,0.472283,0.208307,0.485797,0.196119,0.485556,0.219775,0.50135,0.2196,0.517899,0.219979,0.493568,0.231627,0.493762,0.231436,0.501606,0.231294,0.518228,0.221038,0.448244,0.220938,0.460546,0.232596,0.448369,0.232516,0.460724,0.219864,0.534487,0.231605,0.534705,0.22061,0.472743,0.22018,0.485924,0.232215,0.472874,0.231815,0.486048,0.251113,0.519433,0.251446,0.502571,0.254329,0.502736,0.25396,0.519636,0.251648,0.494488,0.254534,0.494612,0.254568,0.494614,0.254363,0.502738,0.253994,0.51964,0.252559,0.461005,0.252769,0.448502,0.255688,0.44852,0.255453,0.461047,0.255723,0.448519,0.255487,0.461048,0.250931,0.535419,0.253687,0.535536,0.253721,0.535539,0.251847,0.486524,0.252235,0.473174,0.255123,0.473223,0.254736,0.486606,0.255157,0.473223,0.25477,0.486607,0.182764,0.741868,0.182735,0.741905,0.17492,0.73346,0.174946,0.733427,0.182151,0.744756,0.175195,0.736061,0.169983,0.724046,0.171124,0.726315,0.170003,0.724016,0.168282,0.713453,0.168296,0.713428,0.170028,0.715328,0.168161,0.700709,0.170276,0.702192,0.168172,0.700688,0.205201,0.757035,0.205179,0.757084,0.193069,0.749978,0.193095,0.749934,0.203675,0.760262,0.191902,0.753015,0.231715,0.766859,0.2317,0.76691,0.218322,0.76254,0.218339,0.762489,0.23004,0.770284,0.2167,0.765827,0.256697,0.760913,0.256904,0.768854,0.254942,0.764308,0.255079,0.772328,0.255226,0.776315,0.257101,0.77282,0.257159,0.774153,0.257158,0.774195,0.257115,0.774202,0.257131,0.774151,0.255265,0.777694,0.255221,0.777701,0.252076,0.776811,0.253936,0.773325,0.253952,0.773274,0.256505,0.729801,0.256602,0.746809,0.254879,0.732436,0.254919,0.749939,0.256017,0.702103,0.256299,0.71587,0.25443,0.703851,0.254694,0.718045,0.168195,0.588545,0.168188,0.588549,0.168496,0.569509,0.168503,0.569506,0.170531,0.588609,0.170828,0.569523,0.16894,0.551392,0.171243,0.551352,0.168946,0.551391,0.253662,0.551401,0.25393,0.568279,0.252479,0.551529,0.252874,0.56833,0.254351,0.58634,0.25332,0.586336,0.169432,0.534069,0.169438,0.534071,0.171682,0.533885,0.169837,0.517179,0.172024,0.516865,0.169843,0.517182,0.170595,0.459343,0.170587,0.459343,0.170628,0.446667,0.170635,0.446669,0.172842,0.459419,0.172993,0.446669,0.170704,0.431973,0.173162,0.431812,0.170711,0.431977,0.254748,0.605954,0.253651,0.605956,0.255065,0.625956,0.253848,0.626038,0.168083,0.628931,0.168076,0.628937,0.168104,0.608753,0.168111,0.608749,0.170409,0.629293,0.170446,0.608935,0.167852,0.666032,0.167844,0.666043,0.167934,0.647781,0.167942,0.647774,0.170148,0.666838,0.170258,0.648331,0.255242,0.645064,0.253893,0.645319,0.255408,0.664289,0.253942,0.664894,0.167967,0.68433,0.167976,0.684315,0.170235,0.68545,0.255693,0.684176,0.25415,0.685352,0.17102,0.413059,0.171027,0.413064,0.173521,0.412699,0.17139,0.393518,0.173908,0.393025,0.171397,0.393525,0.255889,0.396517,0.255906,0.415708,0.254209,0.396177,0.254251,0.415458,0.255862,0.433987,0.254165,0.43386,0.171844,0.360977,0.171838,0.360971,0.172118,0.346327,0.172123,0.346333,0.1744,0.360517,0.174733,0.345874,0.172241,0.33205,0.174892,0.331568,0.172245,0.332056,0.255978,0.33119,0.25588,0.346601,0.253728,0.331205,0.253796,0.346574,0.255827,0.362418,0.253906,0.362273,0.171977,0.317741,0.171981,0.317747,0.174606,0.317198,0.171612,0.303948,0.17414,0.30334,0.171616,0.303956,0.256141,0.301588,0.256096,0.316143,0.253618,0.301304,0.253691,0.31606,0.255846,0.378913,0.254069,0.378637,0.171629,0.376475,0.171636,0.376482,0.174163,0.375983,0.171752,0.277454,0.171744,0.277444,0.17287,0.260761,0.172882,0.260777,0.173771,0.276588,0.174505,0.25942,0.174873,0.245594,0.176018,0.243771,0.174889,0.245616,0.177815,0.235695,0.177837,0.23572,0.178368,0.233593,0.182694,0.22802,0.182623,0.225717,0.182719,0.228048,0.255936,0.233442,0.255845,0.253472,0.253426,0.231099,0.253294,0.251795,0.255882,0.272431,0.253292,0.271427,0.200433,0.212279,0.190506,0.219964,0.189848,0.217463,0.199237,0.209608,0.200458,0.212319,0.190532,0.219998,0.211657,0.205592,0.211683,0.205634,0.209984,0.20279,0.224698,0.199368,0.222627,0.196453,0.224723,0.199411,0.252522,0.188392,0.252498,0.188344,0.256693,0.186757,0.256716,0.186806,0.249941,0.185247,0.254072,0.183638,0.254129,0.183635,0.256751,0.186756,0.256753,0.186798,0.256732,0.188172,0.256626,0.192243,0.254123,0.185053,0.254051,0.189134,0.253907,0.197263,0.256442,0.200314,0.256173,0.21457,0.253664,0.211742,0.171429,0.291223,0.171435,0.291231,0.173755,0.290566,0.256025,0.287826,0.253443,0.287271,0.170019,0.500645,0.170025,0.500648,0.172148,0.50035,0.170082,0.492951,0.172201,0.492718,0.170089,0.492953,0.170151,0.485451,0.170144,0.48545,0.17038,0.472063,0.170387,0.472063,0.172254,0.485283,0.172541,0.472057,0.254564,0.494614,0.254359,0.50274,0.252607,0.494629,0.252463,0.502828,0.253992,0.519642,0.252308,0.519851,0.25572,0.448519,0.253922,0.448408,0.255483,0.461047,0.253567,0.460913,0.253721,0.535541,0.252302,0.535739,0.255152,0.473222,0.253146,0.473098,0.254765,0.486607,0.252748,0.486548,0.244518,0.770698,0.244533,0.770646,0.242757,0.77414,0.244136,0.765067,0.244431,0.769198,0.243879,0.757181,0.243893,0.743914,0.243939,0.728154,0.243531,0.701404,0.243783,0.714877,0.243291,0.68391,0.243174,0.664416,0.243296,0.645278,0.243442,0.626117,0.243266,0.586538,0.2434,0.606135,0.243136,0.568464,0.24298,0.551407,0.242765,0.535076,0.242678,0.518846,0.242904,0.502097,0.243098,0.494131,0.243678,0.473036,0.243289,0.486289,0.243985,0.460886,0.244122,0.448452,0.244148,0.43395,0.244119,0.415467,0.244049,0.396091,0.243935,0.362378,0.243955,0.378622,0.244093,0.346721,0.244258,0.331445,0.244262,0.316556,0.244132,0.302238,0.243894,0.288841,0.243647,0.274071,0.24349,0.255978,0.243172,0.236885,0.242438,0.219059,0.241562,0.205654,0.240811,0.198155,0.240293,0.194444,0.240073,0.193087,0.240097,0.193133,0.237703,0.190065,0.25717,0.77414,0.257173,0.774104,0.255277,0.777639,0.253954,0.773204,0.257134,0.774082,0.231718,0.766785,0.244534,0.770574,0.218351,0.762415,0.205228,0.756965,0.193146,0.749876,0.182836,0.741823,0.170092,0.723981,0.175031,0.733388,0.168384,0.713397,0.168256,0.700663,0.168059,0.684294,0.167934,0.666017,0.168022,0.647763,0.168161,0.628923,0.168275,0.588537,0.16819,0.608741,0.168581,0.569499,0.169023,0.551386,0.169512,0.534071,0.169915,0.517186,0.170097,0.50065,0.170161,0.492954,0.170672,0.459344,0.170462,0.472062,0.170224,0.485451,0.170715,0.446673,0.170793,0.431984,0.17111,0.413073,0.171481,0.393534,0.171926,0.360985,0.171719,0.376491,0.172203,0.34634,0.172322,0.332062,0.172057,0.317754,0.171691,0.303963,0.17151,0.29124,0.171827,0.277467,0.172958,0.260795,0.174967,0.245639,0.177915,0.235745,0.182794,0.228079,0.200504,0.212375,0.190593,0.220042,0.211717,0.205694,0.224748,0.199472,0.252531,0.188461,0.240113,0.193198,0.256723,0.186876,0.256774,0.186845,0.256769,0.186809,0.254148,0.183689]]}";
        Model model = JSON.parseObject(text, Model.class);
        assertEquals(text, JSON.toJSONString(model));
        // System.out.println(JSON.toJSONString(model));
    }

    public static class Model {
        public double[][] matrix;
    }
}
