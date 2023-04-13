with Ada.Text_IO, GNAT.Semaphores;
use Ada.Text_IO, GNAT.Semaphores;
with Ada.Containers.Indefinite_Doubly_Linked_Lists;
use Ada.Containers;

procedure Main is

   package String_Lists is new Indefinite_Doubly_Linked_Lists (String);
   use String_Lists;

   procedure Init (StorageSize : in Integer; Target : in Integer;
                   ProducersCount : in Integer; ConsumersCount : in Integer) is
      Storage : List;
      Access_Storage : Counting_Semaphore (1, Default_Ceiling);
      Full_Storage   : Counting_Semaphore (StorageSize, Default_Ceiling);
      Empty_Storage  : Counting_Semaphore (0, Default_Ceiling);
      ProducersWorkDone : Integer := 0;
      ConsumersWorkDone : Integer := 0;

      task type Producer;
      task body Producer is
      begin

         while ProducersWorkDone < Target loop

            Full_Storage.Seize;
            delay 0.25;
            Access_Storage.Seize;

            if ProducersWorkDone >= Target then
               Access_Storage.Release;
               exit;
            end if;

            Storage.Append("item " & ProducersWorkDone'Img);
            Put_Line("Producer added item " & ProducersWorkDone'Img);
            ProducersWorkDone := ProducersWorkDone + 1;

            Access_Storage.Release;
            Empty_Storage.Release;

         end loop;

      end Producer;

      task type Consumer;
      task body Consumer is
      begin

         while ConsumersWorkDone < Target loop

            Empty_Storage.Seize;
            delay 0.25;
            Access_Storage.Seize;

            if ConsumersWorkDone >= Target then
               Access_Storage.Release;
               exit;
            end if;

            declare
               item : String := First_Element (Storage);
            begin
               Put_Line("Consumer took item " & item);
            end;
            Storage.Delete_First;
            ConsumersWorkDone := ConsumersWorkDone + 1;

            Access_Storage.Release;
            Full_Storage.Release;

         end loop;

      end Consumer;

      Consumers : Array(1..ConsumersCount) of Consumer;
      Producers : Array(1..ProducersCount) of Producer;

   begin
      null;
   end Init;

begin
   Init(4, 10, 4, 6);
end Main;
