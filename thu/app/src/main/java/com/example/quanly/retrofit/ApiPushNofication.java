package com.example.quanly.retrofit;

import com.example.quanly.model.MessageData;
import com.example.quanly.model.NotiResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    @Headers(
            {
                    "Content-Type:application/json",
                    //"Authorization:Bearer ya29.c.c0ASRK0GYGDiyDp8VSYIQucq1T0gqX3kvoEqanwIejebRfDXZujuNmX-RiXZ3VMHb_DDHLvzKdHyKcUBRJJJNlgmXlU71hj_-PZhGP7fAX8DXBqv4-cgFSG22-yHdeud3M35kHYvedLvcuTgfSp49x3JBtz3ZGhGrhxoLt4XNJsAOOO3TLCy5UFLwhhZaQED_RjGSmRlRO8qi4KJW55dqGUwx5kuMmKYvkSeYdqu0iKguJS6bxCi0jaznXaeQswS1egN3YrJZmenCx2ECb8-a6JGLPw_vtztwRdSyGUC6qJlLUkLrIfV_dd5MYQVihJB9n3d7tvOa-KBejvlwa3GLcZNH9y-IPZFbPpEY286OyajJ0Vw_1eULIcqNtL385KJpkIjw1fpi5-g6S49jib2YIOXuOhzMIhuJhtj6faf8y-0R506f16rXYmW62nqjWSwUu5kzMvqkOv5xqliQ-u5jQvpyly6t64g9UeMk5xrRqv10Wx7i6JzugoYayVUUeqtOs27hwe1q1h9FMt1401aZv8rbi8eyjSw-iJibsZWBdx-zMvlwd42WYqVls889x_xxIiwfuhnOMkSZpQrcnnzpYYl1Uf74r63IIi0d3ZbcrVmyyX0-S3s7ygOWSqMoRRUq14wtxgWyWdO9kdW0tSfdwZX_hOOOmsu4dj0IyZkRi23968Oplx9X1q4hYxI7QlxeMZ9fRXSg_RXXz8t-F-x8dY1zzrcIb1Fkiac2FkS6vO1V6W4i7Mh3ScyBg2qmhbgrbSMVvg09e2tn4cwIbyFd8F043acllp4q393BxvOWUVuVZgIY67rngaaZVW9f9vOQv1-X2e68yVRs8WQl8_0yFc3VlB0XbydyXY08fXMyUWeyiB84eQa3S2-oz1npI0ssqtzq_r8XattSYYpkhIorecuoQwdfs9dzrtc0_b2ciQZwjdIqoJrbxrFtsZoXRsinIY1a8lnqY1gasQx2wfIMu5flgspMBj54J5orRo--6xXk-4mB8I_eYujs"            }
            }
    )
    @POST("projects/banhang-f86d4/messages:send")
    Observable<NotiResponse> sendNofitication(@Body MessageData data);
}
