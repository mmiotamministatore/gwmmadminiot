package it.mm.iot.gw.admin.service.model;

import it.mm.iot.gw.admin.service.exception.IssueOperationFactory;
import it.mm.iot.gw.admin.service.exception.SeverityEnum;
import it.mm.iot.gw.admin.service.model.asset.Asset;
import it.mm.iot.gw.admin.service.model.asset.AssetBuildingInfo;
import it.mm.iot.gw.admin.service.model.asset.AssetInfo;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class AssetFactory {

    @Autowired
    protected IssueOperationFactory issueOperationFactory;

    @Autowired
    ResourceLoader resourceLoader;

    private Base64 base64 = new Base64(0);

    public <T extends Asset> T newInstance(Class<T> clazz) {
        try {
            T element = clazz.getDeclaredConstructor().newInstance();

            element.setInfo(initAssetInfo());
            element.getInfo().setStatus(AssetStateEnum.Unknown);
            List<Asset> assets = new ArrayList<Asset>();
            element.setElements(assets);

            return element;
        } catch (
            InstantiationException
            | IllegalAccessException
            | IllegalArgumentException
            | InvocationTargetException
            | NoSuchMethodException
            | SecurityException e
        ) {
            // TODO Auto-generated catch block
            throw new RuntimeException(e);
        }
    }

    public AssetTree initAssetTree() {
        AssetTree assetTree = new AssetTree();
        assetTree.setInfo(new AssetTreeInfo());
        assetTree.getInfo().setId(UUID.randomUUID().toString());
        assetTree.getInfo().setDataUltimaVersione(LocalDate.now());
        assetTree.setStructure(new AssetStructure());
        List<Asset> assets = new ArrayList<Asset>();
        assetTree.getStructure().setAssets(assets);
        return assetTree;
    }

    public AssetInfo initAssetInfo() {
        AssetInfo assetInfo = new AssetInfo();

        assetInfo.setId(UUID.randomUUID().toString());
        assetInfo.setDataUltimaVersione(LocalDate.now());

        return assetInfo;
    }

    public AssetBuildingInfo initAssetBuildingInfo() {
        AssetBuildingInfo bim = new AssetBuildingInfo();
        return bim;
    }

    public AssetBuildingInfo loadBuildingInfo(String id) {
        AssetBuildingInfo bim = initAssetBuildingInfo();

        String dataModel = getDataResource(id + ".wexBIM");
        String dataJSON = getDataResource(id + ".json");
        bim.setBimModel(dataModel);
        bim.setBimData(dataJSON);
        return bim;
    }

    private String getDataResource(String id) {
        String data = null;
        try {
            InputStream is = getStreamData(id);
            if (is != null) {
                byte[] bytes = IOUtils.toByteArray(is);
                data = base64.encodeToString(bytes);
            }
        } catch (IOException ex) {
            issueOperationFactory.addIssue(SeverityEnum.FATAL, 0 + "", ex.getLocalizedMessage());
        }
        return data;
    }

    public InputStream getStreamData(String fileName) {
        InputStream in = null;
        Resource resource = resourceLoader.getResource("classpath:xbim/" + fileName);
        try {
            in = resource.getInputStream();
        } catch (IOException ex) {}
        return in;
    }
}
