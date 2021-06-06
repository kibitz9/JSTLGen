/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jstlgen.ComplexFields;
import jstlgen.*;
import java.util.*;
/**
 *
 * @author cmiller
 */
public class SDFEnterprisePrimaryHull extends SignedDistanceField3d{

    @Override
    public double GetRawDistance(Vector3d p) {
        return lathe.GetDistance(p);
    }

    @Override
    public SignedDistanceField3d Clone() {
        return new SDFEnterprisePrimaryHull();
    }
     
    SDF2dPrimitivePolygon primaryHullCrossSection;
    SDFOperationLathe lathe;
    public SDFEnterprisePrimaryHull(){
        primaryHullCrossSection = new SDF2dPrimitivePolygon(
            Arrays.asList(
            
                new Vector2d(0,60)
                , new Vector2d(7,74)
                , new Vector2d(8,75)
                , new Vector2d(9,75.5)
                , new Vector2d(10,75.75)
                , new Vector2d(11,75.8)
                , new Vector2d(26,75.8)
                , new Vector2d(27,75.4)
                , new Vector2d(28,75.2)
                , new Vector2d(29,74.9)
                , new Vector2d(30,74.6)
                , new Vector2d(31,74.7)
                , new Vector2d(32,74.6)
                , new Vector2d(33,74.5)
                , new Vector2d(34,74.35)
                , new Vector2d(35,74.25)
                , new Vector2d(36,74.1)
                , new Vector2d(37,74)
                , new Vector2d(38,73.9)
                , new Vector2d(39,73.8)
                 , new Vector2d(40,73.7)
                , new Vector2d(41,73.6)
                , new Vector2d(42,73.5)
                , new Vector2d(43,73.45)
                , new Vector2d(44,73.3)
                , new Vector2d(45,73.3)


                , new Vector2d(48,73.2)

                , new Vector2d(56,73.3)
                , new Vector2d(57,73.3)
                , new Vector2d(58,73.35)
                , new Vector2d(59,73.4)

                , new Vector2d(60,73.4)
                , new Vector2d(61,73.4)
                , new Vector2d(62,73.5)
                , new Vector2d(63,73.7)
                , new Vector2d(64,73.75)
                , new Vector2d(65,74)
                , new Vector2d(66,74)
                , new Vector2d(67,74.1)
                , new Vector2d(68,74.3)
                , new Vector2d(69,74.4)

                , new Vector2d(70,74.5)
                , new Vector2d(71,74.6)
                , new Vector2d(72,74.85)
                , new Vector2d(73,75)
                , new Vector2d(74,75.2)
                , new Vector2d(75,75.3)
                , new Vector2d(76,75.6)
                , new Vector2d(77,75.8)
                , new Vector2d(78,76)
                , new Vector2d(79,76.3)

                , new Vector2d(80,76.5)
                , new Vector2d(81,76.7)
                , new Vector2d(82,77)
                , new Vector2d(83,77.2)
                , new Vector2d(84,77.5)
                , new Vector2d(85,77.8)
                , new Vector2d(86,78)
                , new Vector2d(87,78.4)
                , new Vector2d(88,78.7)
                , new Vector2d(89,79)



                , new Vector2d(90,79.4)
                , new Vector2d(91,79.8)
                , new Vector2d(92,80.1)
                , new Vector2d(93,80.6)
                , new Vector2d(94,81)
                , new Vector2d(95,81.3)
                , new Vector2d(96,81.7)
                , new Vector2d(97,82.3)
                , new Vector2d(98,82.6)
                , new Vector2d(99,83.1)


                , new Vector2d(100,83.6)
                , new Vector2d(101,84)
                , new Vector2d(102,84.5)
                , new Vector2d(103,85)
                , new Vector2d(104,85.5)
                , new Vector2d(105,86)
                , new Vector2d(106,86.35)
                , new Vector2d(107,86.8)
                , new Vector2d(108,87.2)
                , new Vector2d(109,87.8)


                , new Vector2d(110,88.2)
                , new Vector2d(111,88.7)
                , new Vector2d(112,89.2)
                , new Vector2d(113,89.7)
                , new Vector2d(114,90)
                , new Vector2d(115,90.6)
                , new Vector2d(116,91.05)
                , new Vector2d(117,91.5)
                , new Vector2d(118,92)
                , new Vector2d(119,92.5)

                , new Vector2d(120,92.9)
                , new Vector2d(121,93.2)
                , new Vector2d(122,93.8)
                , new Vector2d(123,94.2)
                , new Vector2d(124,94.6)
                , new Vector2d(125,95.1)
                , new Vector2d(126,95.6)
                , new Vector2d(127,95.9)
                , new Vector2d(128,96.4)
                , new Vector2d(129,97)

                , new Vector2d(130,97.45)
                , new Vector2d(131,97.75)
                , new Vector2d(132,98.2)
                , new Vector2d(133,98.5)
                , new Vector2d(134,98.7)
                , new Vector2d(135,98.9)
                , new Vector2d(136,99.15)
                , new Vector2d(137,99.3)
                , new Vector2d(138,99.4)
                , new Vector2d(139,99.5)

                , new Vector2d(140,99.6)
                , new Vector2d(141,101.5)
                , new Vector2d(142,102.6)
                , new Vector2d(143,102.9)
                , new Vector2d(144,103)
                , new Vector2d(145,103)
                , new Vector2d(146,103)
                , new Vector2d(147,103)
                , new Vector2d(148,103)
                , new Vector2d(149,103.2)

                , new Vector2d(150,103.6)
                , new Vector2d(151,104)
                , new Vector2d(152,104.5)
                , new Vector2d(153,104.8)
                , new Vector2d(154,105.1)
                , new Vector2d(155,105.4)
                , new Vector2d(156,105.5)
                , new Vector2d(157,105.7)
                , new Vector2d(158,105.8)
                , new Vector2d(159,105.9)

                , new Vector2d(160,106)


                //end bottom. got to top...

                , new Vector2d(160,23.2)

                ,new Vector2d(159,23.3)
                ,new Vector2d(158,23.3)
                ,new Vector2d(157,23.35)
                ,new Vector2d(156,23.5)
                ,new Vector2d(155,23.7)
                ,new Vector2d(154,24)
                ,new Vector2d(153,24.2)
                ,new Vector2d(152,24.4)
                ,new Vector2d(151,24.8)
                ,new Vector2d(150,25.1)

                ,new Vector2d(149,26)
                ,new Vector2d(148,26.3)
                ,new Vector2d(147,26.3)
                ,new Vector2d(146,26.3)
                ,new Vector2d(145,26.4)
                ,new Vector2d(144,27)
                ,new Vector2d(143,27.6)
                ,new Vector2d(142,28.6)
                ,new Vector2d(141,33.1)
                ,new Vector2d(140,33.1)

                ,new Vector2d(139,33.2)
                ,new Vector2d(138,33.3)
                ,new Vector2d(137,33.5)
                ,new Vector2d(136,33.8)
                ,new Vector2d(135,34)
                ,new Vector2d(134,34.5)
                ,new Vector2d(133,35)
                ,new Vector2d(132,35.4)
                ,new Vector2d(131,36)
                ,new Vector2d(130,36.9)

                ,new Vector2d(129,37.5)
                ,new Vector2d(128,38.5)
                ,new Vector2d(127,39.9)
                ,new Vector2d(126,40.9)
                ,new Vector2d(125,42.5)
                ,new Vector2d(124,44.2)
                ,new Vector2d(123,47)
                ,new Vector2d(122.4,48.85)
                ,new Vector2d(121,49.2)
                ,new Vector2d(120,49.3)

                ,new Vector2d(119,49.4)
                ,new Vector2d(118,49.5)
                ,new Vector2d(117,49.6)
                ,new Vector2d(116,49.7)
                ,new Vector2d(115,49.8)
                ,new Vector2d(114,50)
                ,new Vector2d(113,50.05)
                ,new Vector2d(112,50.1)
                ,new Vector2d(111,50.3)
                ,new Vector2d(110,50.4)

                ,new Vector2d(109,50.4)
                ,new Vector2d(108,50.5)
                ,new Vector2d(107,50.7)
                ,new Vector2d(106,50.8)
                ,new Vector2d(105,51)
                ,new Vector2d(104,51.05)
                ,new Vector2d(103,51.2)
                ,new Vector2d(102,51.4)
                ,new Vector2d(101,51.5)
                ,new Vector2d(100,51.6)

                ,new Vector2d(99,51.7)
                ,new Vector2d(98,51.85)
                ,new Vector2d(97,52)
                ,new Vector2d(96,52.1)
                ,new Vector2d(95,52.2)
                ,new Vector2d(94,52.4)
                ,new Vector2d(93,52.5)
                ,new Vector2d(92,52.7)
                ,new Vector2d(91,52.8)
                ,new Vector2d(90,53)

                ,new Vector2d(89,53.1)
                ,new Vector2d(88,53.2)
                ,new Vector2d(87,53.3)
                ,new Vector2d(86,53.5)
                ,new Vector2d(85,53.65)
                ,new Vector2d(84,53.85)
                ,new Vector2d(83,54)
                ,new Vector2d(82,54.1)
                ,new Vector2d(81,54.25)
                ,new Vector2d(80,54.4)



                ,new Vector2d(79,54.5)
                ,new Vector2d(78,54.7)
                ,new Vector2d(77,54.9)
                ,new Vector2d(76,55.1)
                ,new Vector2d(75,55.25)
                ,new Vector2d(74,55.4)
                ,new Vector2d(73,55.6)
                ,new Vector2d(72,55.7)
                ,new Vector2d(71,55.9)
                ,new Vector2d(70,56.1)


                ,new Vector2d(69,56.2)
                ,new Vector2d(68,56.4)
                ,new Vector2d(67,56.6)
                ,new Vector2d(66,56.7)
                ,new Vector2d(65,56.8)
                ,new Vector2d(64,57)
                ,new Vector2d(63,57.2)
                ,new Vector2d(62,57.4)
                ,new Vector2d(61,57.6)
                ,new Vector2d(60,57.8)


                ,new Vector2d(59,57.9)
                ,new Vector2d(58,58.1)
                ,new Vector2d(57,58.25)
                ,new Vector2d(56,58.4)
                ,new Vector2d(55,58.6)
                ,new Vector2d(54,58.75)
                ,new Vector2d(53,59)
                ,new Vector2d(52,59.2)
                ,new Vector2d(51,59.3)
                ,new Vector2d(50,59.5)


                ,new Vector2d(49,59.7)
                ,new Vector2d(48,59.9)
                ,new Vector2d(47,60)
                ,new Vector2d(1,60)

            )
            

        );
        primaryHullCrossSection = primaryHullCrossSection.InvertX();
        primaryHullCrossSection = primaryHullCrossSection.AlignFirstPointAtOrigin();

        lathe = new SDFOperationLathe(primaryHullCrossSection, 0);
        
    }
}
