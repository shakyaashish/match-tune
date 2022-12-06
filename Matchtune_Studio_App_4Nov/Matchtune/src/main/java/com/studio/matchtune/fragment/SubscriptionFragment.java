package com.studio.matchtune.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.activity.VideoEditionActivity;
import com.studio.matchtune.utility.ApplicationConstant;

import org.w3c.dom.Text;

import java.util.List;


public class SubscriptionFragment extends DialogFragment  implements BillingProcessor.IBillingHandler  {
   LinearLayout bpackage,ppackage;
    Context context;
    AppCompatButton personalsub,businesssub,subscribelater;
    ImageView arrowcircleplan;
    BottomSheetDialog sheetDialog;



    private BillingProcessor bp;
    private TextView tvStatus;
    private Button btnPremium;
    private TransactionDetails purchaseTransactionDetails = null;
    private TransactionDetails purchaseTransactionDetailsPersonal = null;
    BillingClient  billingClient;
    TextView  soundtradcks1;

    public static SubscriptionFragment newInstance(String title) {
        SubscriptionFragment frag = new SubscriptionFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bpackage = (LinearLayout) view.findViewById(R.id.bpackage);
        ppackage = (LinearLayout) view.findViewById(R.id.ppackage);
        personalsub = (AppCompatButton) view.findViewById(R.id.personalsub);
        businesssub = (AppCompatButton) view.findViewById(R.id.businesssub);
        arrowcircleplan = (ImageView) view.findViewById(R.id.arrowcircleplan);
        subscribelater = (AppCompatButton) view.findViewById(R.id.subscribelater);
        soundtradcks1 = (TextView) view.findViewById(R.id.soundtrackhead);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        bp = new BillingProcessor(getActivity(), getResources().getString(R.string.play_console_license), this);
        bp.initialize();


        businesssub.setVisibility(View.INVISIBLE);



        billingClient = BillingClient.newBuilder(getActivity())
                        .enablePendingPurchases()
                                .setListener(
                                        new PurchasesUpdatedListener() {
                                            @Override
                                            public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
                                                if(billingResult.getResponseCode()==BillingClient.BillingResponseCode.OK && list!=null){
                                             for (Purchase purchase :list){
                                                 verifySubPurchase(purchase);
                                             }
                                                }
                                            }
                                        }
                                ).build();
        etablishConnection();





        subscribelater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ExportFragment fragment = ExportFragment.newInstance("Some Title");
//                fragment.setArguments(bundle);
                fragment.show(fm, "fragment_edit_name");


            }
        });

       ppackage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               ppackage.setBackgroundResource(R.drawable.rounded_transpa_box);
               bpackage.setBackgroundResource(R.drawable.rounded_grey_box);
               businesssub.setVisibility(View.INVISIBLE);
               personalsub.setVisibility(View.VISIBLE);

           }
       });


        bpackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bpackage.setBackgroundResource(R.drawable.rounded_transpa_box);
                ppackage.setBackgroundResource(R.drawable.rounded_grey_box);
                personalsub.setVisibility(View.INVISIBLE);
                businesssub.setVisibility(View.VISIBLE);
            }
        });


        arrowcircleplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        businesssub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();



              /*  sheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetStyle);
                sheetDialog.setContentView(R.layout.bottomsheetforsubscription);
                sheetDialog.setCanceledOnTouchOutside(true);
                RecyclerView recyclerView = sheetDialog.findViewById(R.id.recorderbottoms);
                AppCompatButton finalsubscribe = sheetDialog.findViewById(R.id.finalsubscribe);
                TextView cancelbottom = sheetDialog.findViewById(R.id.cancelbottom);
                finalsubscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("testclick","video exported");
                        VideoEditionActivity mActivity=  new VideoEditionActivity();

                        sheetDialog.dismiss();
                    }
                });

                cancelbottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sheetDialog.dismiss();
                    }
                });


                sheetDialog.show();*/

            }
        });




        personalsub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




          /*      sheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetStyle);
                sheetDialog.setContentView(R.layout.bottomsheetforsubscription);
                sheetDialog.setCanceledOnTouchOutside(true);
                RecyclerView recyclerView = sheetDialog.findViewById(R.id.recorderbottoms);
                AppCompatButton finalsubscribe = sheetDialog.findViewById(R.id.finalsubscribe);
                TextView cancelbottom = sheetDialog.findViewById(R.id.cancelbottom);
                finalsubscribe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("testclick","video exported");
                        VideoEditionActivity mActivity=  new VideoEditionActivity();

                        sheetDialog.dismiss();
                    }
                });

                cancelbottom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sheetDialog.dismiss();
                    }
                });

                sheetDialog.show();*/


            }
        });







    }

    public  void etablishConnection(){
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK) {
                    // The BillingClient is ready. You can query purchases here.
                    showProducts();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                etablishConnection();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    void showProducts() {

        ImmutableList productList = ImmutableList.of(
                //Product 1 = index is 0
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("sub_premium")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build(),

                //Product 2 = index is 1
                QueryProductDetailsParams.Product.newBuilder()
                        .setProductId("test_id_shar")
                        .setProductType(BillingClient.ProductType.SUBS)
                        .build()

        );

        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();

        billingClient.queryProductDetailsAsync(
                params,
                (billingResult, productDetailsList) -> {
                    // Process the result
                    for (ProductDetails productDetails : productDetailsList) {
                        if (productDetails.getProductId().equals("sub_premium")) {
                            List subDetails = productDetails.getSubscriptionOfferDetails();
                            assert subDetails != null;

//                            txt_price.setText(subDetails.get(0).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice()+" Per Month");
//                            txt_price.setOnClickListener(view -> {
//                                launchPurchaseFlow(productDetails);
//                            });
                        }

                        if (productDetails.getProductId().equals("test_id_shar")) {
                            List subDetails = productDetails.getSubscriptionOfferDetails();
                            assert subDetails != null;
//                            Log.d("testOffer",subDetails.get(1).getOfferToken());
//                            offer_btn.setText(subDetails.get(1).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice()+" Per Month");
//                            offer_btn.setOnClickListener(view -> {
//                                launchPurchaseFlow(productDetails);
//                            });
                        }
                    }
                }
        );

    }

    void launchPurchaseFlow(ProductDetails productDetails) {
        assert productDetails.getSubscriptionOfferDetails() != null;
        ImmutableList productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .setOfferToken(productDetails.getSubscriptionOfferDetails().get(0).getOfferToken())
                                .build()
                );
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();
        BillingResult billingResult = billingClient.launchBillingFlow(getActivity(), billingFlowParams);
    }



    void verifySubPurchase(Purchase purchases) {

        AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams
                .newBuilder()
                .setPurchaseToken(purchases.getPurchaseToken())
                .build();

        billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                //user prefs to set premium
                Toast.makeText(getActivity(), "You are a premium user now", Toast.LENGTH_SHORT).show();
                //Setting premium to 1
                // 1 - premium
                // 0 - no premium

//                prefs.setPremium(1);
            }
        });

        Log.d("TAG", "Purchase Token: " + purchases.getPurchaseToken());
        Log.d("TAG", "Purchase Time: " + purchases.getPurchaseTime());
        Log.d("TAG", "Purchase OrderID: " + purchases.getOrderId());
    }

    public void onResume() {
        super.onResume();
        billingClient.queryPurchasesAsync(
                QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(),
                (billingResult, list) -> {
                    if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                        for (Purchase purchase : list) {
                            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && !purchase.isAcknowledged()) {
                                verifySubPurchase(purchase);
                            }
                        }
                    }
                }
        );

    }



      public void showprocessing(){
         dismiss();
          FragmentManager fm =getActivity().getSupportFragmentManager();
          SubscriptionProcessFragment fragment = SubscriptionProcessFragment.newInstance("Some Title");
//                fragment.setArguments(bundle);
          fragment.show(fm, "fragment_edit_name");
      }

    private boolean hasSubscription() {
        if (purchaseTransactionDetails != null) {
            return purchaseTransactionDetails.purchaseInfo != null;
        }
        return false;
    }


    private boolean hasSubscriptionpersonal() {
        if (purchaseTransactionDetailsPersonal != null) {
            return purchaseTransactionDetailsPersonal.purchaseInfo != null;
        }
        return false;
    }


    @Override
    public void onBillingInitialized() {

        Log.d("MainActivity", "onBillingInitialized: ");

        String personal = getResources().getString(R.string.personal);
        String premium = getResources().getString(R.string.premium);
        String testing = getResources().getString(R.string.testing);

//        purchaseTransactionDetails = bp.getSubscriptionTransactionDetails(premium);
        purchaseTransactionDetails = bp.getSubscriptionTransactionDetails(testing);
        purchaseTransactionDetailsPersonal = bp.getSubscriptionTransactionDetails(personal);
      //  purchaseTransactionDetails = bp.getSubscriptionTransactionDetails(premium);

        bp.loadOwnedPurchasesFromGoogle();

        Log.d("testsubcheck", ": " +bp.loadOwnedPurchasesFromGoogle()  );
        Log.d("testsubhassub", ": " +hasSubscription()  );
//        Toast.makeText(getActivity(), "is subcription:  " + hasSubscription(), Toast.LENGTH_SHORT).show();

        Toast toast= Toast.makeText(getActivity(),
                "is all:  " + hasSubscription(), Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
        if(hasSubscription()==true){
            Log.d("testddcheck", ": " +hasSubscription() );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issub","subscriptiondone");
            editor.apply();

        }else{
            Log.d("testddcheckfalse", ": " +hasSubscription()  );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issub","");
            editor.apply();
        }

        personalsub.setOnClickListener(v -> {
            if (bp.isSubscriptionUpdateSupported()) {
                showprocessing();

                bp.subscribe(getActivity(), personal);


            } else {
                Log.d("MainActivity", "onBillingInitialized: Subscription updated is not supported");
            }
        });

        businesssub.setOnClickListener(v -> {
            if (bp.isSubscriptionUpdateSupported()) {
                showprocessing();
                bp.subscribe(getActivity(), premium);
            } else {
                Log.d("MainActivity", "onBillingInitialized: Subscription updated is not supported");
            }
        });

        soundtradcks1.setOnClickListener(v -> {
            if (bp.isSubscriptionUpdateSupported()) {
                showprocessing();
                bp.subscribe(getActivity(), testing);
                Toast toasta= Toast.makeText(getActivity(),
                        "is personal:  " + hasSubscriptionpersonal(), Toast.LENGTH_SHORT);
                toasta.setGravity(Gravity.TOP| Gravity.CENTER_HORIZONTAL, 0, 0);
                toasta.show();
            } else {
                Log.d("MainActivity", "onBillingInitialized: Subscription updated is not supported");
            }
        });

        if (hasSubscription()) {
//            tvStatus.setText("Status: Premium");
            Log.d("hassubscription","Premium");
//            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
//            editor.putString("subscribe",token);
//            Log.d("your token is","token is :  " +token );
//            Log.d("your email is","email is :  " +mEditText.getText().toString() );
//            editor.apply();

        } else {
//            tvStatus.setText("Status: Free");
            Log.d("hassubscription","Free");
        }
    }

    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {
        Log.d("MainActivity", "onProductPurchased: ");
    }

    @Override
    public void onPurchaseHistoryRestored() {
        Log.d("MainActivity", "onPurchaseHistoryRestored: ");

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {
        Log.d("MainActivity", "onBillingError: ");

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }


}