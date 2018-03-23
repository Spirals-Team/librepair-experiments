import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("hj")
@Implements("NodeCache")
public final class NodeCache {
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "Lhh;"
   )
   CacheableNode field2622;
   @ObfuscatedName("q")
   @Export("capacity")
   int capacity;
   @ObfuscatedName("i")
   @Export("remainingCapacity")
   int remainingCapacity;
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      signature = "Lhp;"
   )
   @Export("table")
   HashTable table;
   @ObfuscatedName("l")
   @ObfuscatedSignature(
      signature = "Lgm;"
   )
   @Export("list")
   Node2LinkedList list;

   public NodeCache(int var1) {
      this.field2622 = new CacheableNode();
      this.list = new Node2LinkedList();
      this.capacity = var1;
      this.remainingCapacity = var1;

      int var2;
      for(var2 = 1; var2 + var2 < var1; var2 += var2) {
         ;
      }

      this.table = new HashTable(var2);
   }

   @ObfuscatedName("t")
   @ObfuscatedSignature(
      signature = "(J)Lhh;"
   )
   @Export("get")
   public CacheableNode get(long var1) {
      CacheableNode var3 = (CacheableNode)this.table.get(var1);
      if(var3 != null) {
         this.list.push(var3);
      }

      return var3;
   }

   @ObfuscatedName("q")
   @Export("remove")
   public void remove(long var1) {
      CacheableNode var3 = (CacheableNode)this.table.get(var1);
      if(var3 != null) {
         var3.unlink();
         var3.unlinkDual();
         ++this.remainingCapacity;
      }

   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      signature = "(Lhh;J)V"
   )
   @Export("put")
   public void put(CacheableNode var1, long var2) {
      if(this.remainingCapacity == 0) {
         CacheableNode var4 = this.list.pop();
         var4.unlink();
         var4.unlinkDual();
         if(var4 == this.field2622) {
            var4 = this.list.pop();
            var4.unlink();
            var4.unlinkDual();
         }
      } else {
         --this.remainingCapacity;
      }

      this.table.put(var1, var2);
      this.list.push(var1);
   }

   @ObfuscatedName("a")
   @Export("reset")
   public void reset() {
      this.list.clear();
      this.table.clear();
      this.field2622 = new CacheableNode();
      this.remainingCapacity = this.capacity;
   }
}
