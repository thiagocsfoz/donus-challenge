package com.donus.transactionservice.seeders;

import com.donus.transactionservice.domain.entities.Setting;
import com.donus.transactionservice.repository.SettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SettingDataLoader implements CommandLineRunner {

    @Autowired
    private SettingRepository settingRepository;

    @Override
    public void run(String... args) throws Exception {
        if(settingRepository.count() == 0){
            Setting setting = new Setting();
            setting.setId("1");
            setting.setDepositTax(0.005);
            setting.setWithdrawTax(0.01);
            setting.setTransferTax(0D);

            settingRepository.save(setting);
        }
    }
}
