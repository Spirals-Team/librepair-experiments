import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gp")
public final class class188 {
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      signature = "Lgq;"
   )
   @Export("state")
   static DState state;

   static {
      state = new DState();
   }

   @ObfuscatedName("t")
   @Export("Bzip2Decompressor_decompress")
   public static int Bzip2Decompressor_decompress(byte[] var0, int var1, byte[] var2, int var3, int var4) {
      DState var5 = state;
      synchronized(state) {
         state.strm = var2;
         state.next_in = var4;
         state.out = var0;
         state.next_out = 0;
         state.field2516 = var1;
         state.total_in_lo32 = 0;
         state.total_in_hi32 = 0;
         state.total_out_lo32 = 0;
         state.total_out_hi32 = 0;
         method3479(state);
         var1 -= state.field2516;
         state.strm = null;
         state.out = null;
         return var1;
      }
   }

   @ObfuscatedName("q")
   @ObfuscatedSignature(
      signature = "(Lgq;)V"
   )
   static void method3483(DState var0) {
      byte var2 = var0.out_ch;
      int var3 = var0.out_len;
      int var4 = var0.nblock_used;
      int var5 = var0.k0;
      int[] var6 = LoginPacket.tt;
      int var7 = var0.tPos;
      byte[] var8 = var0.out;
      int var9 = var0.next_out;
      int var10 = var0.field2516;
      int var12 = var0.field2542 + 1;

      label65:
      while(true) {
         if(var3 > 0) {
            while(true) {
               if(var10 == 0) {
                  break label65;
               }

               if(var3 == 1) {
                  if(var10 == 0) {
                     var3 = 1;
                     break label65;
                  }

                  var8[var9] = var2;
                  ++var9;
                  --var10;
                  break;
               }

               var8[var9] = var2;
               --var3;
               ++var9;
               --var10;
            }
         }

         boolean var14 = true;

         byte var1;
         while(var14) {
            var14 = false;
            if(var4 == var12) {
               var3 = 0;
               break label65;
            }

            var2 = (byte)var5;
            var7 = var6[var7];
            var1 = (byte)(var7 & 255);
            var7 >>= 8;
            ++var4;
            if(var1 != var5) {
               var5 = var1;
               if(var10 == 0) {
                  var3 = 1;
                  break label65;
               }

               var8[var9] = var2;
               ++var9;
               --var10;
               var14 = true;
            } else if(var4 == var12) {
               if(var10 == 0) {
                  var3 = 1;
                  break label65;
               }

               var8[var9] = var2;
               ++var9;
               --var10;
               var14 = true;
            }
         }

         var3 = 2;
         var7 = var6[var7];
         var1 = (byte)(var7 & 255);
         var7 >>= 8;
         ++var4;
         if(var4 != var12) {
            if(var1 != var5) {
               var5 = var1;
            } else {
               var3 = 3;
               var7 = var6[var7];
               var1 = (byte)(var7 & 255);
               var7 >>= 8;
               ++var4;
               if(var4 != var12) {
                  if(var1 != var5) {
                     var5 = var1;
                  } else {
                     var7 = var6[var7];
                     var1 = (byte)(var7 & 255);
                     var7 >>= 8;
                     ++var4;
                     var3 = (var1 & 255) + 4;
                     var7 = var6[var7];
                     var5 = (byte)(var7 & 255);
                     var7 >>= 8;
                     ++var4;
                  }
               }
            }
         }
      }

      int var13 = var0.total_out_hi32;
      var0.total_out_hi32 += var10 - var10;
      if(var0.total_out_hi32 < var13) {
         ;
      }

      var0.out_ch = var2;
      var0.out_len = var3;
      var0.nblock_used = var4;
      var0.k0 = var5;
      LoginPacket.tt = var6;
      var0.tPos = var7;
      var0.out = var8;
      var0.next_out = var9;
      var0.field2516 = var10;
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(Lgq;)V"
   )
   static void method3479(DState var0) {
      boolean var4 = false;
      boolean var5 = false;
      boolean var6 = false;
      boolean var7 = false;
      boolean var8 = false;
      boolean var9 = false;
      boolean var10 = false;
      boolean var11 = false;
      boolean var12 = false;
      boolean var13 = false;
      boolean var14 = false;
      boolean var15 = false;
      boolean var16 = false;
      boolean var17 = false;
      boolean var18 = false;
      boolean var19 = false;
      boolean var20 = false;
      boolean var21 = false;
      int var22 = 0;
      int[] var23 = null;
      int[] var24 = null;
      int[] var25 = null;
      var0.blockSize100k = 1;
      if(LoginPacket.tt == null) {
         LoginPacket.tt = new int[var0.blockSize100k * 100000];
      }

      boolean var26 = true;

      while(true) {
         while(var26) {
            byte var1 = method3480(var0);
            if(var1 == 23) {
               return;
            }

            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3480(var0);
            var1 = method3481(var0);
            if(var1 != 0) {
               ;
            }

            var0.field2523 = 0;
            var1 = method3480(var0);
            var0.field2523 = var0.field2523 << 8 | var1 & 255;
            var1 = method3480(var0);
            var0.field2523 = var0.field2523 << 8 | var1 & 255;
            var1 = method3480(var0);
            var0.field2523 = var0.field2523 << 8 | var1 & 255;

            int var36;
            for(var36 = 0; var36 < 16; ++var36) {
               var1 = method3481(var0);
               if(var1 == 1) {
                  var0.field2531[var36] = true;
               } else {
                  var0.field2531[var36] = false;
               }
            }

            for(var36 = 0; var36 < 256; ++var36) {
               var0.inUse[var36] = false;
            }

            int var37;
            for(var36 = 0; var36 < 16; ++var36) {
               if(var0.field2531[var36]) {
                  for(var37 = 0; var37 < 16; ++var37) {
                     var1 = method3481(var0);
                     if(var1 == 1) {
                        var0.inUse[var37 + var36 * 16] = true;
                     }
                  }
               }
            }

            method3500(var0);
            int var39 = var0.nInUse + 2;
            int var40 = method3478(3, var0);
            int var41 = method3478(15, var0);

            for(var36 = 0; var36 < var41; ++var36) {
               var37 = 0;

               while(true) {
                  var1 = method3481(var0);
                  if(var1 == 0) {
                     var0.field2536[var36] = (byte)var37;
                     break;
                  }

                  ++var37;
               }
            }

            byte[] var27 = new byte[6];

            byte var29;
            for(var29 = 0; var29 < var40; var27[var29] = var29++) {
               ;
            }

            for(var36 = 0; var36 < var41; ++var36) {
               var29 = var0.field2536[var36];

               byte var28;
               for(var28 = var27[var29]; var29 > 0; --var29) {
                  var27[var29] = var27[var29 - 1];
               }

               var27[0] = var28;
               var0.field2535[var36] = var28;
            }

            int var38;
            for(var38 = 0; var38 < var40; ++var38) {
               int var50 = method3478(5, var0);

               for(var36 = 0; var36 < var39; ++var36) {
                  while(true) {
                     var1 = method3481(var0);
                     if(var1 == 0) {
                        var0.field2537[var38][var36] = (byte)var50;
                        break;
                     }

                     var1 = method3481(var0);
                     if(var1 == 0) {
                        ++var50;
                     } else {
                        --var50;
                     }
                  }
               }
            }

            for(var38 = 0; var38 < var40; ++var38) {
               byte var2 = 32;
               byte var3 = 0;

               for(var36 = 0; var36 < var39; ++var36) {
                  if(var0.field2537[var38][var36] > var3) {
                     var3 = var0.field2537[var38][var36];
                  }

                  if(var0.field2537[var38][var36] < var2) {
                     var2 = var0.field2537[var38][var36];
                  }
               }

               method3484(var0.field2538[var38], var0.field2505[var38], var0.field2519[var38], var0.field2537[var38], var2, var3, var39);
               var0.field2541[var38] = var2;
            }

            int var42 = var0.nInUse + 1;
            int var43 = -1;
            byte var44 = 0;

            for(var36 = 0; var36 <= 255; ++var36) {
               var0.field2539[var36] = 0;
            }

            int var56 = 4095;

            int var35;
            int var55;
            for(var35 = 15; var35 >= 0; --var35) {
               for(var55 = 15; var55 >= 0; --var55) {
                  var0.field2521[var56] = (byte)(var55 + var35 * 16);
                  --var56;
               }

               var0.field2534[var35] = var56 + 1;
            }

            int var47 = 0;
            byte var54;
            if(var44 == 0) {
               ++var43;
               var44 = 50;
               var54 = var0.field2535[var43];
               var22 = var0.field2541[var54];
               var23 = var0.field2538[var54];
               var25 = var0.field2519[var54];
               var24 = var0.field2505[var54];
            }

            int var45 = var44 - 1;
            int var51 = var22;

            int var52;
            byte var53;
            for(var52 = method3478(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
               ++var51;
               var53 = method3481(var0);
            }

            int var46 = var25[var52 - var24[var51]];

            while(true) {
               while(var46 != var42) {
                  if(var46 != 0 && var46 != 1) {
                     int var33 = var46 - 1;
                     int var30;
                     if(var33 < 16) {
                        var30 = var0.field2534[0];

                        for(var1 = var0.field2521[var30 + var33]; var33 > 3; var33 -= 4) {
                           int var34 = var30 + var33;
                           var0.field2521[var34] = var0.field2521[var34 - 1];
                           var0.field2521[var34 - 1] = var0.field2521[var34 - 2];
                           var0.field2521[var34 - 2] = var0.field2521[var34 - 3];
                           var0.field2521[var34 - 3] = var0.field2521[var34 - 4];
                        }

                        while(var33 > 0) {
                           var0.field2521[var30 + var33] = var0.field2521[var30 + var33 - 1];
                           --var33;
                        }

                        var0.field2521[var30] = var1;
                     } else {
                        int var31 = var33 / 16;
                        int var32 = var33 % 16;
                        var30 = var0.field2534[var31] + var32;

                        for(var1 = var0.field2521[var30]; var30 > var0.field2534[var31]; --var30) {
                           var0.field2521[var30] = var0.field2521[var30 - 1];
                        }

                        ++var0.field2534[var31];

                        while(var31 > 0) {
                           --var0.field2534[var31];
                           var0.field2521[var0.field2534[var31]] = var0.field2521[var0.field2534[var31 - 1] + 16 - 1];
                           --var31;
                        }

                        --var0.field2534[0];
                        var0.field2521[var0.field2534[0]] = var1;
                        if(var0.field2534[0] == 0) {
                           var56 = 4095;

                           for(var35 = 15; var35 >= 0; --var35) {
                              for(var55 = 15; var55 >= 0; --var55) {
                                 var0.field2521[var56] = var0.field2521[var0.field2534[var35] + var55];
                                 --var56;
                              }

                              var0.field2534[var35] = var56 + 1;
                           }
                        }
                     }

                     ++var0.field2539[var0.seqToUnseq[var1 & 255] & 255];
                     LoginPacket.tt[var47] = var0.seqToUnseq[var1 & 255] & 255;
                     ++var47;
                     if(var45 == 0) {
                        ++var43;
                        var45 = 50;
                        var54 = var0.field2535[var43];
                        var22 = var0.field2541[var54];
                        var23 = var0.field2538[var54];
                        var25 = var0.field2519[var54];
                        var24 = var0.field2505[var54];
                     }

                     --var45;
                     var51 = var22;

                     for(var52 = method3478(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                        ++var51;
                        var53 = method3481(var0);
                     }

                     var46 = var25[var52 - var24[var51]];
                  } else {
                     int var48 = -1;
                     int var49 = 1;

                     do {
                        if(var46 == 0) {
                           var48 += var49;
                        } else if(var46 == 1) {
                           var48 += var49 * 2;
                        }

                        var49 *= 2;
                        if(var45 == 0) {
                           ++var43;
                           var45 = 50;
                           var54 = var0.field2535[var43];
                           var22 = var0.field2541[var54];
                           var23 = var0.field2538[var54];
                           var25 = var0.field2519[var54];
                           var24 = var0.field2505[var54];
                        }

                        --var45;
                        var51 = var22;

                        for(var52 = method3478(var22, var0); var52 > var23[var51]; var52 = var52 << 1 | var53) {
                           ++var51;
                           var53 = method3481(var0);
                        }

                        var46 = var25[var52 - var24[var51]];
                     } while(var46 == 0 || var46 == 1);

                     ++var48;
                     var1 = var0.seqToUnseq[var0.field2521[var0.field2534[0]] & 255];

                     for(var0.field2539[var1 & 255] += var48; var48 > 0; --var48) {
                        LoginPacket.tt[var47] = var1 & 255;
                        ++var47;
                     }
                  }
               }

               var0.out_len = 0;
               var0.out_ch = 0;
               var0.field2528[0] = 0;

               for(var36 = 1; var36 <= 256; ++var36) {
                  var0.field2528[var36] = var0.field2539[var36 - 1];
               }

               for(var36 = 1; var36 <= 256; ++var36) {
                  var0.field2528[var36] += var0.field2528[var36 - 1];
               }

               for(var36 = 0; var36 < var47; ++var36) {
                  var1 = (byte)(LoginPacket.tt[var36] & 255);
                  LoginPacket.tt[var0.field2528[var1 & 255]] |= var36 << 8;
                  ++var0.field2528[var1 & 255];
               }

               var0.tPos = LoginPacket.tt[var0.field2523] >> 8;
               var0.nblock_used = 0;
               var0.tPos = LoginPacket.tt[var0.tPos];
               var0.k0 = (byte)(var0.tPos & 255);
               var0.tPos >>= 8;
               ++var0.nblock_used;
               var0.field2542 = var47;
               method3483(var0);
               if(var0.field2542 + 1 == var0.nblock_used && var0.out_len == 0) {
                  var26 = true;
                  break;
               }

               var26 = false;
               break;
            }
         }

         return;
      }
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "(Lgq;)B"
   )
   static byte method3480(DState var0) {
      return (byte)method3478(8, var0);
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "(Lgq;)B"
   )
   static byte method3481(DState var0) {
      return (byte)method3478(1, var0);
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      signature = "(ILgq;)I"
   )
   static int method3478(int var0, DState var1) {
      while(var1.total_in_lo32 < var0) {
         var1.total_in_hi32 = var1.total_in_hi32 << 8 | var1.strm[var1.next_in] & 255;
         var1.total_in_lo32 += 8;
         ++var1.next_in;
         ++var1.total_out_lo32;
         if(var1.total_out_lo32 == 0) {
            ;
         }
      }

      int var3 = var1.total_in_hi32 >> var1.total_in_lo32 - var0 & (1 << var0) - 1;
      var1.total_in_lo32 -= var0;
      return var3;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      signature = "(Lgq;)V"
   )
   static void method3500(DState var0) {
      var0.nInUse = 0;

      for(int var1 = 0; var1 < 256; ++var1) {
         if(var0.inUse[var1]) {
            var0.seqToUnseq[var0.nInUse] = (byte)var1;
            ++var0.nInUse;
         }
      }

   }

   @ObfuscatedName("x")
   static void method3484(int[] var0, int[] var1, int[] var2, byte[] var3, int var4, int var5, int var6) {
      int var7 = 0;

      int var8;
      for(var8 = var4; var8 <= var5; ++var8) {
         for(int var9 = 0; var9 < var6; ++var9) {
            if(var8 == var3[var9]) {
               var2[var7] = var9;
               ++var7;
            }
         }
      }

      for(var8 = 0; var8 < 23; ++var8) {
         var1[var8] = 0;
      }

      for(var8 = 0; var8 < var6; ++var8) {
         ++var1[var3[var8] + 1];
      }

      for(var8 = 1; var8 < 23; ++var8) {
         var1[var8] += var1[var8 - 1];
      }

      for(var8 = 0; var8 < 23; ++var8) {
         var0[var8] = 0;
      }

      int var10 = 0;

      for(var8 = var4; var8 <= var5; ++var8) {
         var10 += var1[var8 + 1] - var1[var8];
         var0[var8] = var10 - 1;
         var10 <<= 1;
      }

      for(var8 = var4 + 1; var8 <= var5; ++var8) {
         var1[var8] = (var0[var8 - 1] + 1 << 1) - var1[var8];
      }

   }
}
